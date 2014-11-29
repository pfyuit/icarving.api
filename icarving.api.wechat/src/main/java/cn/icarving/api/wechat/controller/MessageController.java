package cn.icarving.api.wechat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.icarving.api.wechat.message.send.WxImageCustomerMessage;
import cn.icarving.api.wechat.message.send.WxImageGroupBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxImageOpenidBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxMusicCustomerMessage;
import cn.icarving.api.wechat.message.send.WxNewsAllBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxNewsCustomerMessage;
import cn.icarving.api.wechat.message.send.WxNewsGroupBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxNewsOpenidBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxTemplateMessage;
import cn.icarving.api.wechat.message.send.WxTextCustomerMessage;
import cn.icarving.api.wechat.message.send.WxTextGroupBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxTextOpenidBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxVideoCustomerMessage;
import cn.icarving.api.wechat.message.send.WxVoiceCustomerMessage;
import cn.icarving.api.wechat.message.send.WxVoiceGroupBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxVoiceOpenidBroadcastMessage;
import cn.icarving.api.wechat.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController {
	private static Logger LOGGER = Logger.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/recv", method = RequestMethod.GET)
	public @ResponseBody
	String recvMessageGet(@RequestParam(value = "signature", required = true) String signature, @RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce, @RequestParam(value = "echostr", required = true) String echostr) {
		// FIXME: verify the signature
		return echostr;
	}

	@RequestMapping(value = "/recv", method = RequestMethod.POST)
	public @ResponseBody
	void recvMessagePost(HttpServletRequest request, HttpServletResponse response) {
		try {
			BufferedReader br = request.getReader();
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}
			LOGGER.info("Received: \r\n" + sb.toString());
			String result = messageService.processMessage(sb.toString());

			LOGGER.info("Send: \r\n" + result);
			response.setContentType("application/xml;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/sendCustomer/text", method = RequestMethod.POST)
	public @ResponseBody
	void sendCustomerText(@RequestBody WxTextCustomerMessage msg) {
		messageService.sendCustomer(msg);
	}

	@RequestMapping(value = "/sendCustomer/image", method = RequestMethod.POST)
	public @ResponseBody
	void sendCustomerImage(@RequestBody WxImageCustomerMessage msg) {
		messageService.sendCustomer(msg);
	}

	@RequestMapping(value = "/sendCustomer/voice", method = RequestMethod.POST)
	public @ResponseBody
	void sendCustomerVoice(@RequestBody WxVoiceCustomerMessage msg) {
		messageService.sendCustomer(msg);
	}

	@RequestMapping(value = "/sendCustomer/video", method = RequestMethod.POST)
	public @ResponseBody
	void sendCustomerVideo(@RequestBody WxVideoCustomerMessage msg) {
		messageService.sendCustomer(msg);
	}

	@RequestMapping(value = "/sendCustomer/music", method = RequestMethod.POST)
	public @ResponseBody
	void sendCustomerMusic(@RequestBody WxMusicCustomerMessage msg) {
		messageService.sendCustomer(msg);
	}

	@RequestMapping(value = "/sendCustomer/news", method = RequestMethod.POST)
	public @ResponseBody
	void sendCustomerNews(@RequestBody WxNewsCustomerMessage msg) {
		messageService.sendCustomer(msg);
	}

	@RequestMapping(value = "/broadcastCustomer/all/news", method = RequestMethod.POST)
	public @ResponseBody
	void broadcastCustomerAllNews(@RequestBody WxNewsAllBroadcastMessage msg) {
		messageService.broadcastCustomerAllNews(msg);
	}

	@RequestMapping(value = "/broadcastCustomer/group/news", method = RequestMethod.POST)
	public @ResponseBody
	void broadcastCustomerGroupNews(@RequestBody WxNewsGroupBroadcastMessage msg) {
		messageService.broadcastCustomerGroupNews(msg);
	}

	@RequestMapping(value = "/broadcastCustomer/group/text", method = RequestMethod.POST)
	public @ResponseBody
	void broadcastCustomerGroupText(@RequestBody WxTextGroupBroadcastMessage msg) {
		messageService.broadcastCustomerGroupText(msg);
	}

	@RequestMapping(value = "/broadcastCustomer/group/voice", method = RequestMethod.POST)
	public @ResponseBody
	void broadcastCustomerGroupVoice(@RequestBody WxVoiceGroupBroadcastMessage msg) {
		messageService.broadcastCustomerGroupVoice(msg);
	}

	@RequestMapping(value = "/broadcastCustomer/group/image", method = RequestMethod.POST)
	public @ResponseBody
	void broadcastCustomerGroupImage(@RequestBody WxImageGroupBroadcastMessage msg) {
		messageService.broadcastCustomerGroupImage(msg);
	}

	@RequestMapping(value = "/broadcastCustomer/openid/news", method = RequestMethod.POST)
	public @ResponseBody
	void broadcastCustomerOpenidNews(@RequestBody WxNewsOpenidBroadcastMessage msg) {
		messageService.broadcastCustomerOpenidNews(msg);
	}

	@RequestMapping(value = "/broadcastCustomer/openid/text", method = RequestMethod.POST)
	public @ResponseBody
	void broadcastCustomerOpenidText(@RequestBody WxTextOpenidBroadcastMessage msg) {
		messageService.broadcastCustomerOpenidText(msg);
	}

	@RequestMapping(value = "/broadcastCustomer/openid/voice", method = RequestMethod.POST)
	public @ResponseBody
	void broadcastCustomerOpenidVoice(@RequestBody WxVoiceOpenidBroadcastMessage msg) {
		messageService.broadcastCustomerOpenidVoice(msg);
	}

	@RequestMapping(value = "/broadcastCustomer/openid/image", method = RequestMethod.POST)
	public @ResponseBody
	void broadcastCustomerOpenidImage(@RequestBody WxImageOpenidBroadcastMessage msg) {
		messageService.broadcastCustomerOpenidImage(msg);
	}
	
	@RequestMapping(value = "/template/send", method = RequestMethod.POST)
	public @ResponseBody
	void sendTemplateMessage(@RequestBody WxTemplateMessage msg) {
		messageService.sendTemplateMessage(msg);
	}

}
