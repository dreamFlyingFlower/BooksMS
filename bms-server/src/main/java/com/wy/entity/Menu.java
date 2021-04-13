package com.wy.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.google.common.base.MoreObjects;
import com.wy.annotation.CheckAdd;
import com.wy.annotation.CheckUpdate;
import com.wy.annotation.Sort;
import com.wy.config.UserConfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table("ti_menu")
public class Menu extends BaseBean<Menu> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(hump = true)
	@CheckUpdate
	private Integer menuId;

	// 菜单名称
	@Column(hump = true)
	@CheckAdd
	private String menuName;

	// 菜单url
	@Column(hump = true)
	@CheckAdd
	private String menuUrl;

	// 父级菜单
	@Column(hump = true)
	@CheckAdd
	private Integer parentId;

	// 菜单图标,必选,默认star.svg
	@Column(hump = true)
	private String menuIcon = UserConfig.defaultMenuIcon;
	
	@Column(hump=true)
	private String menuI18n;

	@Column(hump = true)
	@Sort
	private Integer sort;

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues().add("menuId", menuId)
				.add("menuName", menuName).add("menuUrl", menuUrl).add("parentId", parentId)
				.add("menuI18n", menuI18n).add("sort", sort)
				.toString();
	}
}