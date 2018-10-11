package com.wy.dao;

import org.springframework.stereotype.Repository;

import com.wy.entity.User;

/**
 * user数据库层
 * @author paradiseWy 2018年8月28日
 */
@Repository
public class UserDao extends BaseDao<User> {

	/**
	 * 检查各种帐号类型帐号是否注册,默认用户名
	 * @param account 帐号
	 * @param type 帐号类型1用户名,2email,3是身份证号
	 */
	public boolean hasAccount(String account, Integer type) {
		if (null == type || 1 == type) {
			return hasValue("username", account);
		} else if (2 == type) {
			return hasValue("email", account);
		} else if (3 == type) {
			return hasValue("idcard", account);
		}
		return false;
	}
}