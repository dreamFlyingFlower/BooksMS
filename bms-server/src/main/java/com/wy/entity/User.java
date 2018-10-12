package com.wy.entity;

import java.util.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.wy.annotation.CheckAdd;
import com.wy.annotation.CheckUpdate;
import com.wy.common.Constant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * user实体类,多部门,多角色,暂未实现
 * 
 * @author paradiseWy 2018年8月28日
 */
@Table("ti_user")
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseBean<User> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(hump = true)
	@CheckUpdate
	private Integer userId;
	@Column(hump = true)
	@CheckAdd
	private String username;
	@JSONField(serialize = false)
	@Column(hump = true)
	@CheckAdd
	private String password;
	@Column(hump = true)
	private String realname;
	@Column(hump = true)
	private Integer departId;
	@Column(hump = true)
	private String idcard;
	@Column(hump = true)
	private Date birthday;
	@Column(hump = true)
	private Integer age;
	@Column(hump = true)
	private Character sex;
	@Column(hump = true)
	private String address;
	@Column(hump = true)
	private String email;
	@Column(hump = true)
	private String tel;
	@Column(hump = true)
	private String salary;
	@Column(hump = true)
	private Integer state = 1;
	@Column(hump = true)
	private String userIcon = Constant.DEFAULT_USER_ICON;
	@Column(hump = true)
	@Readonly
	private Date createtime;
	@Column(hump = true)
	@Readonly
	private Date updatetime;

	private String token;
	private List<Depart> departs;
	private List<Role> roles;
	private List<Button> buttons;
}