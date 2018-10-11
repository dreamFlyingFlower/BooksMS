package com.wy.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.google.common.base.MoreObjects;
import com.wy.annotation.CheckAdd;
import com.wy.annotation.CheckUpdate;
import com.wy.annotation.Sort;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table("ti_depart")
public class Depart extends BaseBean<Depart> {
	@Id
	@Column(hump = true)
	@CheckUpdate
	private Integer departId;

	@Column(hump = true)
	@CheckAdd
	private String departName;

	@Column(hump = true)
	@CheckAdd
	private Integer parentId;

	// 排序
	@Column(hump = true)
	@Sort
	private Integer sort;

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues().add("departId", departId)
				.add("departName", departName).add("parentId", parentId).add("sort", sort).toString();
	}
}