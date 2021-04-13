package com.wy.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.Table;

import com.google.common.base.MoreObjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Table("ti_related_file")
public class RelatedFile extends BaseBean<RelatedFile> {
    @Id
    @Column(hump=true)
    private Integer fileId;

    //存储在本地的名称,规则是yyyyMMdd_文件后缀_32uuid
    @Column(hump=true)
    private String localName;

    //文件本来的名字
    @Column(hump=true)
    private String fileName;

    //文件类型1图片2音频3视频4文本5其他
    @Column(hump=true)
    private Integer fileType;

    //文件大小,单位M
    @Column(hump=true)
    private BigDecimal fileSize;

    //音视频文件时长,单位秒
    @Column(hump=true)
    private String fileTime;
    
    //文件后缀,不需要点
    @Column(hump=true)
    private String fileSuffix;
    
    @Column(hump=true)
    @Readonly
    private Date uploadtime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("fileId", fileId)
                .add("localName", localName)
                .add("fileName", fileName)
                .add("fileType", fileType)
                .add("fileSize", fileSize)
                .add("fileTime", fileTime)
                .toString();
    }
}