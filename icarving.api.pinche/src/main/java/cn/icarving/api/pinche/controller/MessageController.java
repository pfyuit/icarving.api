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

import com.google.common.collect.Lists;

import cn.icarving.api.pinche.common.ApiEnum;
import cn.icarving.api.pinche.common.ApiMessage;
import cn.icarving.api.pinche.common.ApiResponse;
import cn.icarving.api.pinche.domain.Message;
import cn.icarving.api.pinche.dto.SendUserMessageForm;
import cn.icarving.api.pinche.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService userMessageService;

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse readMessage(@RequestParam(value = "msgId", required = true) int msgId) {
		userMessageService.readUserMessage(msgId);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

	@RequestMapping(value = "/{uid}/all", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findAllMessagesByUser(@PathVariable(value = "uid") int uid) {
		List<Message> messages = userMessageService.findAllMessagesByUser(uid);
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), messages);
	}

	@RequestMapping(value = "/{activityId}/allmessage", method = RequestMethod.GET)
	public @ResponseBody
	ApiResponse findAllMessagesByActivity(@PathVariable(value = "activityId") int activityId) {
		List<Message> messages = userMessageService.findAllMessagesByActivity(activityId);
		List<Message> result = Lists.newArrayList();
		for (Message msg : messages) {
			if (msg.getMessageType() == ApiMessage.MESSAGE_TYPE_MESSAGE) {
				result.add(msg);
			}
		}
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), result);
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public @ResponseBody
	ApiResponse sendUserMessage(@RequestBody SendUserMessageForm form) {
		userMessageService.createUserMessage(form.getMessageType(), form.getActivityId(), form.getActivitySourceAddress(), form.getActivityDestAddress(), form.getApplyId(),
				form.getFromUid(), form.getToUid(), form.getToName(), form.getContent(), form.getIsReply());
		return new ApiResponse(ApiEnum.API_SUCCESS.getCode(), ApiEnum.API_SUCCESS.getMessage(), null);
	}

}
