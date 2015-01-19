package cn.icarving.api.pinche.controller;

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
import cn.icarving.api.pinche.dto.UserUpdateForm;
import cn.icarving.api.pinche.dto.WechatRegisterOrLoginForm;
import cn.icarving.api.pinche.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse userRegister(@RequestBody RegisterForm registerForm) {
		User user = userService.findUserByUserName(registerForm.getUsername());
		if (user != null) {
			throw new ApiException(ApiEnum.USER_ALREADY_REGISTERED.getCode(), ApiEnum.USER_ALREADY_REGISTERED.getMessage());
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
		User user = userService.findUserByUserName(username);
		if (user == null) {
			throw new ApiException(ApiEnum.USER_CANNOT_FIND.getCode(), ApiEnum.USER_CANNOT_FIND.getMessage());
		}
		if (!user.getPassword().equals(password)) {
			throw new ApiException(ApiEnum.USER_PASSWORD_NOT_MATCH.getCode(), ApiEnum.USER_PASSWORD_NOT_MATCH.getMessage());
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
		user.setSex(wechatRegisterOrLoginForm.getSex());
		user.setCountry(wechatRegisterOrLoginForm.getCountry());
		user.setProvince(wechatRegisterOrLoginForm.getProvince());
		user.setCity(wechatRegisterOrLoginForm.getCity());
		user.setAvatar(wechatRegisterOrLoginForm.getAvatar());

		userService.register(user);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
	}

	@RequestMapping(value = "/logoff", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse userLogoff(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password) {
		User user = userService.findUserByUserName(username);
		if (user == null) {
			throw new ApiException(ApiEnum.USER_CANNOT_FIND.getCode(), ApiEnum.USER_CANNOT_FIND.getMessage());
		}
		if (!user.getPassword().equals(password)) {
			throw new ApiException(ApiEnum.USER_PASSWORD_NOT_MATCH.getCode(), ApiEnum.USER_PASSWORD_NOT_MATCH.getMessage());
		}

		userService.logoff(user);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse userUpdate(@RequestBody UserUpdateForm form) {
		User user = userService.updateUser(form);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse userInfo(@RequestParam(value = "uid", required = true) int uid) {
		User user = userService.findUserByUid(uid);
		if (user == null) {
			throw new ApiException(ApiEnum.USER_CANNOT_FIND.getCode(), ApiEnum.USER_CANNOT_FIND.getMessage());
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), user);
	}

}
