package cn.icarving.api.wechat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.icarving.api.wechat.message.recv.WxImageMessage;
import cn.icarving.api.wechat.message.recv.WxLinkMessage;
import cn.icarving.api.wechat.message.recv.WxLocationMessage;
import cn.icarving.api.wechat.message.recv.WxTextMessage;
import cn.icarving.api.wechat.message.recv.WxVideoMessage;
import cn.icarving.api.wechat.message.recv.WxVoiceMessage;
import cn.icarving.api.wechat.oxm.XMLProcessor;

@Controller
@RequestMapping("/message")
public class MessageController {
	private static Logger LOGGER = Logger.getLogger(MessageController.class);

	@RequestMapping(value = "/recv", method = RequestMethod.GET)
	public @ResponseBody
	String recvMessage(@RequestParam(value = "signature", required = true) String signature, @RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce, @RequestParam(value = "echostr", required = true) String echostr) {
		// FIXME: verify the signature
		return echostr;
	}

	@RequestMapping(value = "/recv", method = RequestMethod.POST)
	public @ResponseBody
	void recvMessagePOST(HttpServletRequest request) {
		try {
			BufferedReader br = request.getReader();
			char[] buf = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while ((len = br.read(buf)) != -1) {
				sb.append(buf, 0, len);
			}
			LOGGER.info("\r\n"+sb.toString());
			doRequestGateway(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doRequestGateway(String string) {
		String msgType = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(string);
			InputSource is = new InputSource(sr);
			Document doc = db.parse(is);
			XPathFactory factory = XPathFactory.newInstance();
			XPath path = factory.newXPath();
			msgType = path.evaluate("/xml/MsgType", doc);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (msgType.equals("text")) {
			processTextMessage(string);
		} else if (msgType.equals("image")) {
			processImageMessage(string);
		} else if (msgType.equals("voice")) {
			processVoiceMessage(string);
		} else if (msgType.equals("video")) {
			processVideoMessage(string);
		} else if (msgType.equals("location")) {
			processLocationMessage(string);
		} else if (msgType.equals("link")) {
			processLinkMessage(string);
		} // TODO
	}

	private void processLinkMessage(String string) {
		XMLProcessor<WxLinkMessage> processor = new XMLProcessor<WxLinkMessage>(WxLinkMessage.class);
		WxLinkMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a link message: " + msg.getMsgId());
		// TODO
	}

	private void processLocationMessage(String string) {
		XMLProcessor<WxLocationMessage> processor = new XMLProcessor<WxLocationMessage>(WxLocationMessage.class);
		WxLocationMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a location message: " + msg.getMsgId());
		// TODO
	}

	private void processVoiceMessage(String string) {
		XMLProcessor<WxVoiceMessage> processor = new XMLProcessor<WxVoiceMessage>(WxVoiceMessage.class);
		WxVoiceMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a voice message: " + msg.getMsgId());
		// TODO
	}

	private void processVideoMessage(String string) {
		XMLProcessor<WxVideoMessage> processor = new XMLProcessor<WxVideoMessage>(WxVideoMessage.class);
		WxVideoMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a video message: " + msg.getMsgId());
		// TODO
	}

	private void processImageMessage(String string) {
		XMLProcessor<WxImageMessage> processor = new XMLProcessor<WxImageMessage>(WxImageMessage.class);
		WxImageMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a image message: " + msg.getMsgId());
		// TODO
	}

	private void processTextMessage(String string) {
		XMLProcessor<WxTextMessage> processor = new XMLProcessor<WxTextMessage>(WxTextMessage.class);
		WxTextMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a text message: " + msg.getMsgId());
		// TODO
	}

}
