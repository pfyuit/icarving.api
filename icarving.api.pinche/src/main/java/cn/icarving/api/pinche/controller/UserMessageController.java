package cn.icarving.api.pinche.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiResponse;
import cn.icarving.api.pinche.domain.UserMessage;
import cn.icarving.api.pinche.dto.SendUserMessageForm;
import cn.icarving.api.pinche.service.UserMessageService;

@Controller
@RequestMapping("/message")
public class UserMessageController {

	@Autowired
	private UserMessageService userMessageService;

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse readMessage(@RequestParam(value = "msgId", required = true) int msgId) {
		userMessageService.readUserMessage(msgId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/{uid}/all", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findAllMessagesByUser(@PathVariable(value = "uid") int uid) {
		List<UserMessage> messages = userMessageService.findAllMessagesByUser(uid);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), messages);
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse sendUserMessage(@RequestBody SendUserMessageForm form) {
		userMessageService.createUserMessage(Integer.parseInt(form.getFromUid()), Integer.parseInt(form.getToUid()), form.getContent());
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

}
