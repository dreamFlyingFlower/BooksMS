package com.wy.entity;

import com.google.common.base.MoreObjects;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

@Getter
@NoArgsConstructor
@Setter
@Table("tb_book")
public class Book extends BaseBean<Book> {
    @Id
    @Column(hump=true)
    private Integer bookId;

    //书名
    @Name
    @Prev(els = @EL("uuid()"))
    @Column(hump=true)
    private String bookName;

    //作者
    @Name
    @Prev(els = @EL("uuid()"))
    @Column(hump=true)
    private String author;

    //书的isbn,国际标准书号
    @Column(hump=true)
    private String isbn;

    //出版公司
    @Column(hump=true)
    private String publishCompany;

    //出版日期
    @Column(hump=true)
    private Date publishDate;

    //入库日期
    @Column(hump=true)
    private Date storageTime;

    //库存总量
    @Column(hump=true)
    private Integer storage;

    //借出
    @Column(hump=true)
    private Integer loan;

    //书的单价
    @Column(hump=true)
    private BigDecimal price;

    //书的类别0其他
    @Column(hump=true)
    private Integer type;

    //书籍封面,对应related_file表的local_name
    @Column(hump=true)
    private String cover;

    //借阅次数
    @Column(hump=true)
    private Integer loanTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("bookId", bookId)
                .add("bookName", bookName)
                .add("author", author)
                .add("isbn", isbn)
                .add("publishCompany", publishCompany)
                .add("publishDate", publishDate)
                .add("storageTime", storageTime)
                .add("storage", storage)
                .add("loan", loan)
                .add("price", price)
                .add("type", type)
                .add("cover", cover)
                .add("loanTime", loanTime)
                .toString();
    }
}