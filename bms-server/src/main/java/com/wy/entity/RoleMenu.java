package com.wy.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.google.common.base.MoreObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Table("tr_role_menu")
public class RoleMenu extends BaseBean<RoleMenu> {
	@Column(hump = true)
	private Integer roleId;

	@Column(hump = true)
	private Integer menuId;

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues().add("roleId", roleId).add("menuId", menuId)
				.toString();
	}
}