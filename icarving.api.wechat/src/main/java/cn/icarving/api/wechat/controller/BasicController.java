package cn.icarving.api.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.wechat.message.basic.GetAccessTokenResponse;
import cn.icarving.api.wechat.message.user.CreateGroupRequest;
import cn.icarving.api.wechat.message.user.CreateGroupResponse;
import cn.icarving.api.wechat.message.user.FindGroupByUserRequest;
import cn.icarving.api.wechat.message.user.FindGroupByUserResponse;
import cn.icarving.api.wechat.message.user.FindGroupResponse;
import cn.icarving.api.wechat.message.user.FindSubscribesResponse;
import cn.icarving.api.wechat.message.user.FindUserResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupResponse;
import cn.icarving.api.wechat.message.user.UpdateNoteRequest;
import cn.icarving.api.wechat.message.user.UpdateNoteResponse;
import cn.icarving.api.wechat.service.BasicService;
import cn.icarving.api.wechat.service.UserService;

@Controller
@RequestMapping("/basic")
public class BasicController {

	@Autowired
	private BasicService basicService;

	@RequestMapping("/accessToken")
	public @ResponseBody
	GetAccessTokenResponse createGroup(@RequestParam(value = "grant_type ", required = true) String grantType, @RequestParam(value = "appid", required = true) String appid,
			@RequestParam(value = "secret", required = true) String secret) {
		return basicService.getAccessToken(grantType, appid, secret);
	}

}
