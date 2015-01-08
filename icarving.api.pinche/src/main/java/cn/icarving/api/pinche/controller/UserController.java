package cn.icarving.api.pinche.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiException;
import cn.icarving.api.pinche.common.ApiResponse;
import cn.icarving.api.pinche.domain.User;
import cn.icarving.api.pinche.dto.RegisterForm;
import cn.icarving.api.pinche.dto.WechatRegisterOrLoginForm;
import cn.icarving.api.pinche.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody
	User userRegister() {
		throw new ApiException("1000001", "wrong request");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse userRegister(@RequestBody RegisterForm registerForm) {
		User user = userService.findUser(registerForm.getUsername());
		if (user != null) {
			throw new ApiException(ApiEnum.USER_REGISTER_FAILED_USER_ALREADY_REGISTERED.getCode(), ApiEnum.USER_REGISTER_FAILED_USER_ALREADY_REGISTERED.getMessage());
		}

		user = new User();
		user.setUsername(registerForm.getUsername());
		user.setPassword(registerForm.getPassword());
		user.setName(registerForm.getName());
		user.setPhone(registerForm.getPhone());

		userService.register(user);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse userLogin(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password) {
		User user = userService.findUser(username);
		if (user == null) {
			throw new ApiException(ApiEnum.USER_LOGIN_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.USER_LOGIN_FAILED_CANNOT_FIND_USER.getMessage());
		}
		if (!user.getPassword().equals(password)) {
			throw new ApiException(ApiEnum.USER_LOGIN_FAILED_PASSWORD_NOT_MATCH.getCode(), ApiEnum.USER_LOGIN_FAILED_PASSWORD_NOT_MATCH.getMessage());
		}

		userService.login(user);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
	}

	@RequestMapping(value = "/wechatRegisterOrLogin", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse wechatRegisterOrLogin(@RequestBody WechatRegisterOrLoginForm wechatRegisterOrLoginForm) {
		User user = userService.findUserByUnionid(wechatRegisterOrLoginForm.getUnionid());
		if (user == null) {
			user = userService.findUserByOpenid(wechatRegisterOrLoginForm.getOpenid());
		}

		// 该微信用户已注册，直接登录
		if (user != null) {
			userLogin(user.getUsername(), user.getPassword());
			return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
		}

		// 注册新微信用户
		user = new User();
		user.setWechatUnionid(wechatRegisterOrLoginForm.getUnionid());
		user.setWechatOpenid(wechatRegisterOrLoginForm.getOpenid());
		user.setUsername(wechatRegisterOrLoginForm.getUsername());
		user.setPassword(wechatRegisterOrLoginForm.getPassword());
		user.setName(wechatRegisterOrLoginForm.getName());
		user.setPhone(wechatRegisterOrLoginForm.getPhone());

		userService.register(user);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
	}

	@RequestMapping(value = "/logoff", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse userLogoff(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password) {
		User user = userService.findUser(username);
		if (user == null) {
			throw new ApiException(ApiEnum.USER_LOGOFF_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.USER_LOGOFF_FAILED_CANNOT_FIND_USER.getMessage());
		}
		if (!user.getPassword().equals(password)) {
			throw new ApiException(ApiEnum.USER_LOGOFF_FAILED_PASSWORD_NOT_MATCH.getCode(), ApiEnum.USER_LOGOFF_FAILED_PASSWORD_NOT_MATCH.getMessage());
		}

		userService.logoff(user);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse userUpdate(@RequestBody User user) {
		User dbUser = userService.findUser(user.getUsername());
		if (dbUser == null) {
			throw new ApiException(ApiEnum.USER_UPDATE_FAILED_CANNOT_FIND_USER.getCode(), ApiEnum.USER_UPDATE_FAILED_CANNOT_FIND_USER.getMessage());
		}
		if (!dbUser.getPassword().equals(user.getPassword())) {
			throw new ApiException(ApiEnum.USER_UPDATE_FAILED_PASSWORD_NOT_MATCH.getCode(), ApiEnum.USER_UPDATE_FAILED_PASSWORD_NOT_MATCH.getMessage());
		}

		userService.updateProfile(user);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
	}

}
