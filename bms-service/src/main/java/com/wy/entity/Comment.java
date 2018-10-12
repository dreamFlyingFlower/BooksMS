package com.wy.entity;

import com.google.common.base.MoreObjects;
import com.wy.entity.BaseBean;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Getter
@NoArgsConstructor
@Setter
@Table("tb_comment")
public class Comment extends BaseBean<Comment> {
    @Id
    @Column(hump=true)
    private Integer commentId;

    //书编号
    @Id
    @Column(hump=true)
    private Integer bookId;

    //评论用户编号
    @Column(hump=true)
    private Integer userId;

    //评论内容
    @Column(hump=true)
    private String content;

    //评论图片,对应ti_related_file的local_name字段,多张用逗号隔开
    @Column(hump=true)
    private String pictures;

    //评论时间
    @Column(hump=true)
    private Date createtime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("commentId", commentId)
                .add("bookId", bookId)
                .add("userId", userId)
                .add("content", content)
                .add("pictures", pictures)
                .add("createtime", createtime)
                .toString();
    }
}