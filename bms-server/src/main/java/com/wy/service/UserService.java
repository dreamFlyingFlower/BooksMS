package com.wy.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.util.cri.Exps;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wy.common.Constant;
import com.wy.common.ResultException;
import com.wy.dao.RoleDao;
import com.wy.dao.UserDao;
import com.wy.entity.RelatedFile;
import com.wy.entity.Role;
import com.wy.entity.User;
import com.wy.enums.TipEnum;
import com.wy.utils.ListUtils;
import com.wy.utils.MapUtils;
import com.wy.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * user的service层
 * @author paradiseWy 2018年8月28日
 */
@Service
@Slf4j
public class UserService extends BaseService<User> {

	@Autowired
	private RelatedFileService fileService;

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	@Override
	public UserDao getDao() {
		return userDao;
	}

	public boolean hasAccount(String account, Integer type) {
		return userDao.hasAccount(account, type);
	}

	/**
	 * 登录
	 * @param account 可以用来登录的帐号,可以有多种类型,在配置文件中
	 * @param password 密码
	 * @return 返回用户名和token
	 */
	public User login(String account, String password) {
		if (StrUtils.isBlank(account)) {
			throw new ResultException(TipEnum.TIP_LOGIN_USERNAME.getErrMsg());
		}
		User user = null;
		String[] accountTypes = Constant.ACCOUNT_TYPE.split(",");
		// 若没有配置类型,默认用户名登录
		if (accountTypes.length == 0) {
			user = userDao.getDao().fetch(User.class,
					Cnd.where(Exps.eq("username", account)).and(Exps.eq("password", password)));
		} else {
			Cnd cnd = Cnd.where(Exps.eq("password", password));
			SqlExpressionGroup group = new SqlExpressionGroup();
			for (String accountType : accountTypes) {
				group.or(Exps.eq(accountType, account));
			}
			user = userDao.getDao().fetch(User.class, cnd.and(group));
		}
		if (user == null) {
			throw new ResultException(TipEnum.TIP_LOGIN.getErrMsg());
		}
		user.setUserIcon(Constant.FILE_HTTP + "/" + user.getUserIcon());
		// 获得角色权限
		StringBuilder sb = new StringBuilder(
				"select c.role_id,c.role_name,c.role_state,c.role_level ").append(
						" from ti_user a inner join tr_user_role b on a.user_id = b.user_id ")
						.append(" inner join ti_role c on b.role_id = c.role_id where a.user_id = @userId");
		List<Role> roles = roleDao.getDao()
				.execute(Sqls.create(sb.toString()).setParam("userId", user.getUserId())
						.setCallback(Sqls.callback.entities()).setEntity(roleDao.getEntry()))
				.getList(Role.class);
		if (ListUtils.isBlank(roles)) {
			throw new ResultException(TipEnum.TIP_ROLE_ERROR.getErrMsg());
		}
		user.setRoles(roles);
		// 一个测试用token,并不检查token是否存在于内存中
		user.setToken("testtoken");
		return user;
	}

	/**
	 * 根据帐号拿到信息,shiro验证适用该方法
	 * @param account 帐号
	 * @return
	 */
	public User getUserByAccount(String username) {
		String[] accountTypes = Constant.ACCOUNT_TYPE.split(",");
		// 若没有配置类型,默认用户名登录
		if (accountTypes.length == 0) {
			return userDao.getDao().fetch(User.class, Cnd.where(Exps.eq("username", username)));
		} else {
			SqlExpressionGroup group = new SqlExpressionGroup();
			for (String accountType : accountTypes) {
				group.or(Exps.eq(accountType, username));
			}
			return userDao.getDao().fetch(User.class, Cnd.where(group));
		}
	}

	@Transactional
	public boolean updateUserIcon(MultipartFile file, Integer userId) {
		User user = userDao.getById(userId);
		if (StrUtils.isNotBlank(user.getUserIcon())) {
			boolean flag = fileService.delByLocalName(user.getUserIcon());
			if (!flag) {
				log.info("删除用户本地头像失败!图片本地名:{0},用户userId:{1}", user.getUserIcon(), userId);
			}
		}
		RelatedFile relatedFile = fileService.uploadFile(file);
		return userDao.update(MapUtils.getBuilder("user_icon", relatedFile.getLocalName()).build(),
				Cnd.where("user_id", "=", userId)) > 0;
	}
}