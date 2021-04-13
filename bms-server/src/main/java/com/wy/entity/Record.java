package com.wy.entity;

import com.google.common.base.MoreObjects;
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
@Table("tb_record")
public class Record extends BaseBean<Record> {
    @Id
    @Column(hump=true)
    private Integer recordId;

    @Id
    @Column(hump=true)
    private Integer bookId;

    @Id
    @Column(hump=true)
    private Integer userId;

    @Column(hump=true)
    private Date loantime;

    //借阅天数
    @Column(hump=true)
    private Integer loanDays;

    //实际归还时间
    @Column(hump=true)
    private Date returntime;

    //是否借阅超期,0否1是,由定时任务完成
    @Column(hump=true)
    private Byte isOver;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("recordId", recordId)
                .add("bookId", bookId)
                .add("userId", userId)
                .add("loantime", loantime)
                .add("loanDays", loanDays)
                .add("returntime", returntime)
                .add("isOver", isOver)
                .toString();
    }
}