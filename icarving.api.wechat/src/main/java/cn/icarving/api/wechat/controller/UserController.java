package cn.icarving.api.wechat.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.wechat.message.user.CreateGroupRequest;
import cn.icarving.api.wechat.message.user.CreateGroupResponse;
import cn.icarving.api.wechat.message.user.FindGroupByUserRequest;
import cn.icarving.api.wechat.message.user.GetAuthUserInfoResponse;
import cn.icarving.api.wechat.message.user.GetGroupByUserResponse;
import cn.icarving.api.wechat.message.user.GetGroupResponse;
import cn.icarving.api.wechat.message.user.GetSubscribesResponse;
import cn.icarving.api.wechat.message.user.GetUserAccessTokenResponse;
import cn.icarving.api.wechat.message.user.GetUserInfoResponse;
import cn.icarving.api.wechat.message.user.RefreshUserAccessTokenResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupResponse;
import cn.icarving.api.wechat.message.user.UpdateNoteRequest;
import cn.icarving.api.wechat.message.user.UpdateNoteResponse;
import cn.icarving.api.wechat.message.user.ValidateUserAccessTokenResponse;
import cn.icarving.api.wechat.message.user.WechatRegisterOrLoginResponse;
import cn.icarving.api.wechat.service.NetworkService;
import cn.icarving.api.wechat.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static Logger LOGGER = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/group/create")
	public @ResponseBody
	CreateGroupResponse createGroup(@RequestBody CreateGroupRequest request) {
		return userService.createGroup(request);
	}

	@RequestMapping("/group/get")
	public @ResponseBody
	GetGroupResponse getGroup() {
		return userService.getGroup();
	}

	@RequestMapping("/group/getByUser")
	public @ResponseBody
	GetGroupByUserResponse getGroupByUser(@RequestBody FindGroupByUserRequest request) {
		return userService.getGroupByUser(request);
	}

	@RequestMapping("/group/update")
	public @ResponseBody
	UpdateGroupResponse updateGroup(@RequestBody UpdateGroupRequest request) {
		return userService.updateGroup(request);
	}

	@RequestMapping("/group/updateByUser")
	public @ResponseBody
	UpdateGroupByUserResponse updateGroupByUser(@RequestBody UpdateGroupByUserRequest request) {
		return userService.updateGroupByUser(request);
	}

	@RequestMapping("/note/update")
	public @ResponseBody
	UpdateNoteResponse updateNote(@RequestBody UpdateNoteRequest request) {
		return userService.updateNote(request);
	}

	@RequestMapping("/info/get")
	public @ResponseBody
	GetUserInfoResponse getUserInfo(@RequestParam(value = "openid", required = true) String openid, @RequestParam(value = "lang", required = true) String lang) {
		return userService.getUserInfo(openid, lang);
	}

	@RequestMapping("/subscribes/get")
	public @ResponseBody
	GetSubscribesResponse getSubscribes(@RequestParam(value = "next_openid ", required = true) String nextOpenid) {
		return userService.getSubscribes(nextOpenid);
	}

	@RequestMapping("/auth/callback")
	public @ResponseBody
	void authCallback(@RequestParam(value = "code", required = true) String code, @RequestParam(value = "state", required = true) String state, HttpServletResponse response) {
		LOGGER.info("code: " + code + ", state=" + state);

		GetUserAccessTokenResponse getUserAccessTokenResponse = getUserAccessToken(NetworkService.WECHAT_PUBLIC_APP_ID, NetworkService.WECHAT_PUBLIC_APP_SECRET, code,
				"authorization_code");
		LOGGER.info("access token: " + getUserAccessTokenResponse.getAccess_token() + ", openid: " + getUserAccessTokenResponse.getOpenid());

		GetAuthUserInfoResponse getAuthUserInfoResponse = getAuthUserInfo(getUserAccessTokenResponse.getAccess_token(), getUserAccessTokenResponse.getOpenid(), "zh_CN");
		LOGGER.info("openid: " + getAuthUserInfoResponse.getOpenid() + ", unionid: " + getAuthUserInfoResponse.getUnionid() + ", nickname: "
				+ getAuthUserInfoResponse.getNickname() + ", sex: " + getAuthUserInfoResponse.getSex() + ", country: " + getAuthUserInfoResponse.getCountry() + ", province: "
				+ getAuthUserInfoResponse.getProvince() + ", city: " + getAuthUserInfoResponse.getCity() + ", headimgurl: " + getAuthUserInfoResponse.getHeadimgurl());

		WechatRegisterOrLoginResponse wechatRegisterOrLoginResponse = userService.registerOrLoginPincheUser(getAuthUserInfoResponse.getUnionid(),
				getAuthUserInfoResponse.getOpenid(), getAuthUserInfoResponse.getNickname(), getAuthUserInfoResponse.getSex(), getAuthUserInfoResponse.getCountry(),
				getAuthUserInfoResponse.getProvince(), getAuthUserInfoResponse.getCity(), getAuthUserInfoResponse.getHeadimgurl());
		LOGGER.info("User: " + wechatRegisterOrLoginResponse.getResponse().getUsername() + "  registered or logged in the pinche application");

		try {
			String url = "http://www.icarving.cn/pinche/#/tab/view?uid=" + wechatRegisterOrLoginResponse.getResponse().getUid() + "&username="
					+ wechatRegisterOrLoginResponse.getResponse().getUsername() + "&password=" + wechatRegisterOrLoginResponse.getResponse().getPassword();
			response.sendRedirect(url);
		} catch (IOException e) {
			LOGGER.error("Redirect to url failed");
			e.printStackTrace();
		}
	}

	@RequestMapping("/auth/token/get")
	public @ResponseBody
	GetUserAccessTokenResponse getUserAccessToken(@RequestParam(value = "appid", required = true) String appid, @RequestParam(value = "secret", required = true) String secret,
			@RequestParam(value = "code", required = true) String code, @RequestParam(value = "grant_type", required = true) String grantType) {
		return userService.getUserAccessToken(appid, secret, code, grantType);
	}

	@RequestMapping("/auth/token/refresh")
	public @ResponseBody
	RefreshUserAccessTokenResponse refreshUserAccessToken(@RequestParam(value = "appid", required = true) String appid,
			@RequestParam(value = "grant_type", required = true) String grantType, @RequestParam(value = "fresh_token", required = true) String refreshToken) {
		return userService.refreshUserAccessToken(appid, grantType, refreshToken);
	}

	@RequestMapping("/auth/token/validate")
	public @ResponseBody
	ValidateUserAccessTokenResponse validateUserAccessToken(@RequestParam(value = "access_token", required = true) String accessToken,
			@RequestParam(value = "openid", required = true) String openid) {
		return userService.validateUserAccessToken(accessToken, openid);
	}

	@RequestMapping("/auth/info/get")
	public @ResponseBody
	GetAuthUserInfoResponse getAuthUserInfo(@RequestParam(value = "access_token", required = true) String accessToken,
			@RequestParam(value = "openid", required = true) String openid, @RequestParam(value = "lang", required = true) String lang) {
		return userService.getAuthUserInfo(accessToken, openid, lang);
	}

}
