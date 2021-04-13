package com.wy.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.wy.common.ResultException;
import com.wy.dao.RelatedFileDao;
import com.wy.entity.RelatedFile;
import com.wy.entity.RelatedFile.RelatedFileBuilder;
import com.wy.utils.FilesUtils;
import com.wy.utils.ListUtils;
import com.wy.utils.StrUtils;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

@Service("fileService")
public class RelatedFileService extends BaseService<RelatedFile> {
	@Autowired
	private RelatedFileDao fileDao;

	@Override
	public RelatedFileDao getDao() {
		return fileDao;
	}

	@Transactional
	public RelatedFile uploadFile(MultipartFile file) {
		String localName = FilesUtils.saveFile(file);
		if (StrUtils.isBlank(localName)) {
			throw new ResultException("file save to local failed");
		}
		return add(handlerFile(file, localName));
	}

	@Transactional
	public List<RelatedFile> uploadFiles(MultipartFile[] files) {
		List<RelatedFile> relatedFiles = new ArrayList<>();
		for (MultipartFile file : files) {
			String localName = FilesUtils.saveFile(file);
			if (StrUtils.isBlank(localName)) {
				throw new ResultException("file save to local failed");
			}
			relatedFiles.add(add(handlerFile(file,localName)));
		}
		return relatedFiles;
	}

	@Transactional
	public boolean delById(Integer fileId) {
		RelatedFile relatedFile = fileDao.getById(fileId);
		if (relatedFile != null) {
			if (fileDao.delete(fileId) > 0) {
				return FilesUtils.delFile(relatedFile.getLocalName());
			}
		}
		return true;
	}

	@Transactional
	public boolean delByLocalName(String localName) {
		if (fileDao.delByLocalName(localName)) {
			return FilesUtils.delFile(localName);
		}
		return false;
	}

	@Transactional
	public boolean delByLocalNames(List<String> localNames) {
		if (ListUtils.isNotBlank(localNames)) {
			for (String localName : localNames) {
				delByLocalName(localName);
			}
			return true;
		}
		return false;
	}

	/**
	 * 文件保存到本地之后网数据库插入数据
	 * @param extention 文件后缀,不带点
	 * @param localName 存在本地的文件名,带后缀,存入数据库的带后缀
	 * @param fileName 文件原本的名字,存入数据库的不带后缀
	 * @return RelatedFile
	 */
	private RelatedFile handlerFile(MultipartFile file, String localName) {
		String originalName = file.getOriginalFilename();
		String extension = Files.getFileExtension(originalName);
		RelatedFileBuilder fileBuilder = RelatedFile.builder().localName(localName)
				.fileName(Files.getNameWithoutExtension(originalName))
				.fileSize(new BigDecimal(file.getSize()).divide(new BigDecimal(1024 * 1024), 2,
						RoundingMode.HALF_UP))
				.fileTime("").fileSuffix(extension);
		// 若是音视频文件,添加时长,格式HH:mm:ss
		int type = FilesUtils.getFileType(extension);
		fileBuilder.fileType(type);
		if (type == 2 || type == 3) {
			try {
				Encoder encoder = new Encoder();
				MultimediaInfo m = encoder.getInfo(FilesUtils.getLocalPath(localName));
				fileBuilder.fileTime(FilesUtils.getFileTime(m.getDuration() / 1000));
			} catch (EncoderException e) {
				e.printStackTrace();
			}
		}
		return fileBuilder.build();
	}
}