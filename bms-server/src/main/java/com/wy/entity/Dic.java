package com.wy.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.google.common.base.MoreObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table("ti_dictionary")
public class Dic extends BaseBean<Dic> {
    @Id
    @Column(hump=true)
    private Integer dicId;

    //字典名
    @Column(hump=true)
    private String dicName;

    //唯一标识符,不可重复
    @Column(hump=true)
    private String dicCode;

    //上级字典
    @Column(hump=true)
    private Integer parentId;

    //排序
    @Column(hump=true)
    private Integer sort;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("dicId", dicId)
                .add("dicName", dicName)
                .add("dicCode", dicCode)
                .add("parentId", parentId)
                .add("sort", sort)
                .toString();
    }
}