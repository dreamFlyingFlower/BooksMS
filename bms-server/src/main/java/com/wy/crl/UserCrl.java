package com.wy.crl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wy.entity.User;
import com.wy.page.UserPage;
import com.wy.result.Result;
import com.wy.service.UserService;

/**
 * user控制层
 * @author paradiseWy 2018年8月28日
 */
@RestController
@RequestMapping("user")
public class UserCrl extends BaseCrl<User> {

	@Autowired
	private UserService userService;

	@Override
	public UserService getService() {
		return userService;
	}

	/**
	 * 用户名是否重复
	 * @param account 需要检验的帐号字符串,可以是username和email
	 * @param type 1是用户名username,2是email,3是身份证号
	 */
	@GetMapping("/hasUser")
	public Result hasAccount(String account, Integer type) {
		return Result.ok(userService.hasAccount(account, type));
	}

	/**
	 * 登录并拿到用户相关信息
	 * @param account 帐号,可以是用户名,邮箱或其他
	 * @param password 密码,未加密
	 */
	@GetMapping("/login")
	public Result login(String account, String password) {
		return Result.ok(userService.login(account, password));
	}

	/**
	 * 上传用户图片
	 * @param file 用户图片
	 * @param userId 用户编号
	 */
	@PostMapping("/uploadUserIcon/{userId}")
	public Result uploadUserIcon(@RequestParam(value="file") MultipartFile file,
			@PathVariable Integer userId) {
		return Result.result(userService.updateUserIcon(file, userId));
	}

	@GetMapping("/getPages")
	public Result getPages(UserPage page) {
		return userService.getPages(page);
	}
}