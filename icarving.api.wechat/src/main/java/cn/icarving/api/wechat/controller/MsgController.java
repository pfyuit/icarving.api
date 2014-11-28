package cn.icarving.api.wechat.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.wechat.oxm.WxMessage;

@Controller
@RequestMapping("/message")
public class MsgController {

	@RequestMapping(value = "/recv", method = RequestMethod.GET)
	public @ResponseBody String recvMessage(@RequestParam(value = "signature", required = true) String signature, @RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce, @RequestParam(value = "echostr", required = true) String echostr) {
		// FIXME: verify the signature
		return echostr;
	}

	@RequestMapping(value = "/recv", method = RequestMethod.POST)
	public @ResponseBody  String recvMessagePOST(@RequestParam(value = "signature", required = true) String signature, @RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce, @RequestParam(value = "echostr", required = true) String echostr, @RequestBody WxMessage msg) {
		// FIXME: verify the signature
		return echostr;
	}

}
