package com.wy.crl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wy.entity.RelatedFile;
import com.wy.result.Result;
import com.wy.service.RelatedFileService;
import com.wy.utils.FilesUtils;

@RestController
@RequestMapping("relatedFile")
public class RelatedFileCrl {
	@Autowired
	private RelatedFileService fileService;

	/**
	 * 上传文件
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Result upload(@RequestParam CommonsMultipartFile file) {
		return Result.ok(fileService.uploadFile(file));
	}

	/**
	 * 批量上传文件
	 */
	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
	public Result uploadFiles(@RequestParam CommonsMultipartFile[] file) {
		return Result.ok(fileService.uploadFiles(file));
	}

	/**
	 * 根据文件编号删除文件,物理删除
	 */
	@RequestMapping(value = "/delById/{fileId}", method = RequestMethod.GET)
	public Result delById(@PathVariable Integer fileId) {
		return Result.result(fileService.delById(fileId));
	}

	/**
	 * 根据文件的本地名称删除,物理删除
	 */
	@RequestMapping(value = "/delByLocalName/{localName}", method = RequestMethod.GET)
	public Result delByLocalName(@PathVariable String localName) {
		return Result.result(fileService.delByLocalName(localName));
	}

	/**
	 * 文件批量删除,物理删除
	 */
	@RequestMapping(value = "/deletes", method = RequestMethod.POST)
	public Result delFile(@RequestBody List<String> localNames) {
		return Result.result(fileService.delByLocalNames(localNames));
	}

	/**
	 * 下载文件
	 * @param req 请求
	 * @param fileId 文件编号
	 */
	@RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(HttpServletRequest req, @PathVariable Integer fileId) {
		RelatedFile fileInfo = fileService.getById(fileId);
		if (fileInfo == null) {
			return null;
		}
		File file = FilesUtils.getLocalPath(fileInfo.getLocalName());
		HttpHeaders headers = new HttpHeaders();
		// 下载解决中文文件名乱码
		String downName = new String(fileInfo.getFileName().getBytes(Charset.defaultCharset()),
				Charset.forName("ISO_8859_1"));
		// 通知浏览器以attachment方式打开图片
		headers.setContentDispositionFormData("attachment", downName);
		// 二进制文件流
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		try {
			return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}