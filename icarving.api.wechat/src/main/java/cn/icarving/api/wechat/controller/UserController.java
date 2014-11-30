package cn.icarving.api.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.wechat.message.user.CreateGroupRequest;
import cn.icarving.api.wechat.message.user.CreateGroupResponse;
import cn.icarving.api.wechat.message.user.FindGroupByUserRequest;
import cn.icarving.api.wechat.message.user.FindGroupByUserResponse;
import cn.icarving.api.wechat.message.user.FindGroupResponse;
import cn.icarving.api.wechat.message.user.FindUserResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupByUserResponse;
import cn.icarving.api.wechat.message.user.UpdateGroupRequest;
import cn.icarving.api.wechat.message.user.UpdateGroupResponse;
import cn.icarving.api.wechat.message.user.UpdateNoteRequest;
import cn.icarving.api.wechat.message.user.UpdateNoteResponse;
import cn.icarving.api.wechat.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/group/create")
	public @ResponseBody
	CreateGroupResponse createGroup(@RequestBody CreateGroupRequest request) {
		return userService.createGroup(request);
	}

	@RequestMapping("/group/find")
	public @ResponseBody
	FindGroupResponse findGroup() {
		return userService.findGroup();
	}

	@RequestMapping("/group/findByUser")
	public @ResponseBody
	FindGroupByUserResponse findGroupByUser(@RequestBody FindGroupByUserRequest request) {
		return userService.findGroupByUser(request);
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
	
	@RequestMapping("/info/find")
	public @ResponseBody
	FindUserResponse findUser(@RequestParam(value="openid", required =true) String openid, @RequestParam(value="lang", required=true) String lang) {
		return userService.findUser(openid, lang);
	}

	@RequestMapping("/callback")
	public String authCallback() {
		return null;
	}

}
