package com.wy.dao;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.util.cri.Exps;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.wy.common.ResultException;
import com.wy.entity.Role;
import com.wy.entity.RoleButton;
import com.wy.entity.RoleMenu;
import com.wy.result.Result;
import com.wy.utils.ListUtils;
import com.wy.utils.ObjUtils;

@Repository
public class RoleDao extends BaseDao<Role> {

	/**
	 * 角色数据分页查询,不查询超级管理员
	 */
	public Result getRoles(Role bean) {
		if (bean.hasPage()) {
			return Result.page(
					dao.query(Role.class, Cnd.where(Exps.eq("role_state", 1)),
							new Pager(bean.getPageIndex(), bean.getPageSize())),
					bean.getPageIndex(), bean.getPageSize(), dao.count(Role.class));
		} else {
			return Result.result(dao.query(Role.class, Cnd.where(Exps.eq("role_state", 1))));
		}
	}

	/**
	 * 根据角色权限查询菜单树,不查询按钮
	 * 
	 * @param roleId 角色编号
	 * @return 菜单树
	 */
	public List<Map<String, Object>> getRoleMenu(int roleId) {
		Role role = getById(roleId);
		if (null == role) {
			throw new ResultException("不存在这个角色!");
		}
		// 是否是超级管理员
		boolean isSuperAdmin = role.getRoleState() == 0;
		List<Map<String, Object>> rootChildren = getChildrenMenu(isSuperAdmin, roleId, 0);
		if (ListUtils.isBlank(rootChildren)) {
			throw new ResultException("该角色没有分配菜单!");
		}
		for (Map<String, Object> rootChild : rootChildren) {
			getRoleMenu(isSuperAdmin, roleId, rootChild);
		}
		return rootChildren;
	}

	private void getRoleMenu(boolean isSuperAdmin, int roleId, Map<String, Object> rootChild) {
		List<Map<String, Object>> children = getChildrenMenu(isSuperAdmin, roleId,
				Integer.parseInt(rootChild.get("menuId").toString()));
		if (ListUtils.isBlank(children)) {
			return;
		}
		rootChild.put("children", children);
		for (Map<String, Object> child : children) {
			getRoleMenu(isSuperAdmin, roleId, child);
		}
	}

	/**
	 * 根据角色查询菜单子菜单
	 */
	private List<Map<String, Object>> getChildrenMenu(boolean isSuperAdmin, int roleId,
			int menuId) {
		StringBuilder sb = new StringBuilder("select c.menu_id menuId,c.menu_name menuName,")
				.append("c.parent_id parentId,c.menu_url menuUrl,c.menu_icon menuIcon,")
				.append("c.menu_i18n menuI18n");
		if (isSuperAdmin) {
			return getMaps(
					Sqls.create(sb.append(" from ti_menu c where c.parent_id = @menuId").toString())
							.setParam("menuId", menuId));
		} else {
			sb.append(" from ti_role a inner join tr_role_menu b on a.role_id = b.role_id ")
					.append(" inner join ti_menu c on c.menu_id = b.menu_id ")
					.append(" where a.role_id = @roleId and c.parent_id = @menuId");
			return getMaps(Sqls.create(sb.toString()).setParam("roleId", roleId).setParam("menuId",
					menuId));
		}
	}

	/**
	 * 根据角色权限查询菜单树,以及菜单下所有按钮
	 * 
	 * @param roleId 角色编号
	 */
	public List<Map<String, Object>> getRoleMenus(int roleId) {
		Role role = getById(roleId);
		if (null == role) {
			throw new ResultException("不存在这个角色!");
		}
		// 是否是超级管理员
		boolean isSuperAdmin = role.getRoleState() == 0;
		List<Map<String, Object>> menus = getChildrenMenus(isSuperAdmin, roleId, 0);
		if (ListUtils.isBlank(menus)) {
			throw new ResultException("该角色没有分配菜单!");
		}
		addChildMenu(isSuperAdmin, roleId, menus);
		return menus;
	}

	private void addChildMenu(boolean isSuperAdmin, int roleId, List<Map<String, Object>> maps) {
		for (Map<String, Object> map : maps) {
			int menuId = NumberUtils.parseNumber(String.valueOf(map.get("menuId")), int.class);
			if (ObjUtils.positiveNum(map.get("btnNum"))) {
				map.put("button", getMenuButton(roleId, menuId));
			}
			if (ObjUtils.positiveNum(map.get("childNum"))) {
				List<Map<String, Object>> childs = getChildrenMenus(isSuperAdmin, roleId, menuId);
				addChildMenu(isSuperAdmin, roleId, childs);
				map.put("children", childs);
			}
		}
	}

	/**
	 * 根据角色查询菜单子菜单以及按钮
	 * 
	 * @param roleId 角色编号
	 * @param menuId 菜单编号
	 */
	private List<Map<String, Object>> getChildrenMenus(boolean isSuperAdmin, int roleId,
			int menuId) {
		StringBuilder sb = new StringBuilder("select a.menu_id menuId,a.menu_name menuName,")
				.append("(select count(*) from ti_button b where b.menu_id = a.menu_id) btnNum,")
				.append("(select count(*) from ti_menu c where c.parent_id = a.menu_id) childNum,");
		if (isSuperAdmin) {
			return getMaps(Sqls.create(
					sb.append("1 isCheck from ti_menu a where a.parent_id=@menuId order by a.sort")
							.toString())
					.setParam("menuId", menuId));
		} else {
			sb.append(
					"ifnull((select 1 from tr_role_menu d where d.menu_id = a.menu_id and d.role_id = @roleId), 0)")
					.append(" isCheck  from ti_menu a where a.parent_id = @menuId order by a.sort");
			return getMaps(Sqls.create(sb.toString()).setParam("roleId", roleId).setParam("menuId",
					menuId));
		}
	}

	/**
	 * 查询权限按钮
	 * 
	 * @param roleId 角色编号
	 * @param menuId 菜单编号
	 * @return
	 */
	private List<Map<String, Object>> getMenuButton(int roleId, int menuId) {
		String sql = "select b.button_id buttonId,b.button_name buttonName,"
				+ "ifnull(select 1 from tr_role_button a where a.button_id = b.button_id and a.role_id = @roleId,0) isCheck "
				+ " from ti_button b where b.menu_id = @menuId order by b.sort";
		return getMaps(Sqls.create(sql).setParam("roleId", roleId).setParam("menuId", menuId));
	}

	/**
	 * 权限保存
	 * 
	 * @param roleId 角色编号
	 * @param data 需要保存的数据,其中每条数据的isCheck为1的时候表示保存
	 */
	public void saveMenus(int roleId, List<Map<String, Object>> data) {
		if (getById(roleId) == null) {
			throw new ResultException("不存在这个角色");
		}
		dao.clear(RoleMenu.class, Cnd.where(Exps.eq("role_id", roleId)));
		dao.clear(RoleButton.class, Cnd.where(Exps.eq("role_id", roleId)));
		saveMenu(roleId, data);
	}

	@SuppressWarnings("unchecked")
	private void saveMenu(int roleId, List<Map<String, Object>> menus) {
		if (ListUtils.isBlank(menus)) {
			return;
		}
		for (Map<String, Object> map : menus) {
			if (map.get("isCheck") != null && (Integer) map.get("isCheck") == 1) {
				RoleMenu rm = new RoleMenu();
				rm.setRoleId(roleId);
				rm.setMenuId(Integer.parseInt(map.get("menuId").toString()));
				dao.insert(rm);

				List<Map<String, Object>> buttons = (List<Map<String, Object>>) map.get("button");
				if (ListUtils.isNotBlank(buttons)) {
					for (Map<String, Object> btn : buttons) {
						if (btn.get("isCheck") != null && (Integer) btn.get("isCheck") == 1) {
							RoleButton rb = new RoleButton();
							rb.setRoleId(roleId);
							rb.setButtonId((Integer) btn.get("buttonId"));
							dao.insert(rb);
						}
					}
				}
			}
			List<Map<String, Object>> child = (List<Map<String, Object>>) map.get("children");
			saveMenu(roleId, child);
		}
	}
}