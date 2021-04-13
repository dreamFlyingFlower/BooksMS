package com.wy.crl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wy.entity.Role;
import com.wy.service.RoleService;
import com.wy.utils.Result;

@RestController
@RequestMapping("role")
public class RoleCrl extends BaseCrl<Role> {
	@Autowired
	private RoleService roleService;

	@Override
	public RoleService getService() {
		return roleService;
	}

	/**
	 * 新增角色菜单
	 */
	@PostMapping("/saveMenus/{roleId}")
	public Result saveMenus(@PathVariable int roleId, @RequestBody List<Map<String, Object>> datas) {
		roleService.saveMenus(roleId, datas);
		return Result.resultOk();
	}

	/**
	 * 查询角色表
	 */
	@PostMapping("/getRoles")
	public Result getRoles(@RequestBody Role bean) {
		return roleService.getRoles(bean);
	}

	/**
	 * 查询角色菜单分页
	 */
	@GetMapping("/getRoleMenu/{roleId}")
	public Result getRoleMenu(@PathVariable Integer roleId) {
		return Result.resultOk(roleService.getRoleMenu(roleId));
	}

	/**
	 * 查询角色菜单按钮分页
	 */
	@PostMapping("/getRoleMenus/{roleId}")
	public Result getRoleMenus(@PathVariable Integer roleId) {
		return Result.resultOk(roleService.getRoleMenus(roleId));
	}
}