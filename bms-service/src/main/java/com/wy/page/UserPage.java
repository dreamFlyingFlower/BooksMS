//package com.wy.page;
//
//import java.util.Objects;
//
//import org.nutz.dao.Dao;
//import org.nutz.dao.sql.GroupBy;
//import org.nutz.dao.sql.OrderBy;
//import org.nutz.dao.util.cri.SqlExpressionGroup;
//import org.nutz.lang.Strings;
//
//import com.wy.page.BasePage;
//
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * 自定义参数分页,查询符合条件的用户及相关信息
// * 
// * @author paradiseWy 2018年8月28日
// */
//@Getter
//@Setter
//public class UserPage extends BasePage {
//
//	private static final long serialVersionUID = 1L;
//	private String username;
//	private String realname;
//	private String address;
//	private String beginTime;
//	private String endTime;
//	private Integer userType;
//	private Integer state;
//	private Integer roleId;
//	private String roleName;
//
//	@Override
//	public String addColumns() {
//		return new StringBuilder(
//				" a.userId,a.username,a.realname,a.birthday,a.age,a.sex,a.address,a.email,a.tel,")
//						.append("c.roleName ").toString();
//	}
//
//	@Override
//	public String addSpecialColumns() {
//		return null;
//	}
//
//	@Override
//	public String addTables() {
//		return new StringBuilder(" ti_user a left join tr_user_role b on a.user_id = b.user_id ")
//				.append(" left join ti_role c on c.role_id = b.role_id ").toString();
//	}
//
//	@Override
//	public void addCnds(Dao dao, SqlExpressionGroup where) {
//		where.andNotEquals("IFNULL(c.role_state,'-1')", 0);
//		if (Strings.isNotBlank(username)) {
//			where.andLikeL(" a.username ", username);
//		}
//		if (Strings.isNotBlank(realname)) {
//			where.andLike(" a.realname ", realname);
//		}
//		if (Strings.isNotBlank(address)) {
//			where.andLike(" a.address ", address);
//		}
//		if (Strings.isNotBlank(beginTime)) {
//			where.and(" a.birthday ", ">=", beginTime);
//		}
//		if (Strings.isNotBlank(endTime)) {
//			where.and(" a.birthday ", "<=", endTime);
//		}
//		if (Objects.nonNull(userType)) {
//			where.andEquals("a.userType", userType);
//		}
//		if (Objects.nonNull(roleId)) {
//			where.andEquals("c.roleId", roleId);
//		}
//		if (Objects.nonNull(state)) {
//			where.andEquals(" a.state", state);
//		}
//		if (Strings.isNotBlank(roleName)) {
//			where.andLike(" c.roleName ", roleName);
//		}
//	}
//
//	@Override
//	public void addGroup(GroupBy group) {
//	}
//
//	@Override
//	public void addOrder(OrderBy order) {
//		order.asc(" a.user_id");
//	}
//}