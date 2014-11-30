package cn.icarving.api.wechat.service;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.icarving.api.wechat.message.recv.Wx2DCodeEventMessage;
import cn.icarving.api.wechat.message.recv.WxImageMessage;
import cn.icarving.api.wechat.message.recv.WxLinkMessage;
import cn.icarving.api.wechat.message.recv.WxLocationEventMessage;
import cn.icarving.api.wechat.message.recv.WxLocationMessage;
import cn.icarving.api.wechat.message.recv.WxMenuEventMessage;
import cn.icarving.api.wechat.message.recv.WxRecognitionMessage;
import cn.icarving.api.wechat.message.recv.WxSubEventMessage;
import cn.icarving.api.wechat.message.recv.WxTextMessage;
import cn.icarving.api.wechat.message.recv.WxVideoMessage;
import cn.icarving.api.wechat.message.recv.WxVoiceMessage;
import cn.icarving.api.wechat.message.send.WxImageGroupBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxImageOpenidBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxNewsAllBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxNewsGroupBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxNewsOpenidBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxNewsResMessage;
import cn.icarving.api.wechat.message.send.WxResMessageBuilder;
import cn.icarving.api.wechat.message.send.WxTemplateMessage;
import cn.icarving.api.wechat.message.send.WxTextGroupBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxTextOpenidBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxTextResMessage;
import cn.icarving.api.wechat.message.send.WxVoiceGroupBroadcastMessage;
import cn.icarving.api.wechat.message.send.WxVoiceOpenidBroadcastMessage;
import cn.icarving.api.wechat.oxm.XMLProcessor;

import com.google.common.base.Strings;

@Service
public class MessageService {

	@Autowired
	private NetworkService serviceService;

	private static Logger LOGGER = Logger.getLogger(MessageService.class);

	public String processMessage(String string) {
		String response = "";

		String msgType = null;
		String event = null;
		String eventKey = null;
		String recognition = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(string);
			InputSource is = new InputSource(sr);
			Document doc = db.parse(is);
			XPathFactory factory = XPathFactory.newInstance();
			XPath path = factory.newXPath();
			msgType = path.evaluate("/xml/MsgType", doc);
			event = path.evaluate("/xml/Event", doc);
			eventKey = path.evaluate("/xml/EventKey", doc);
			recognition = path.evaluate("/xml/Recognition", doc);
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
			response = processTextMessage(string);
		} else if (msgType.equals("image")) {
			response = processImageMessage(string);
		} else if (msgType.equals("voice") && Strings.isNullOrEmpty(recognition)) {
			response = processVoiceMessage(string);
		} else if (msgType.equals("video")) {
			response = processVideoMessage(string);
		} else if (msgType.equals("location")) {
			response = processLocationMessage(string);
		} else if (msgType.equals("link")) {
			response = processLinkMessage(string);
		} else if (msgType.equals("event") && (event.equals("subscribe") || event.equals("unsubscribe")) && Strings.isNullOrEmpty(eventKey)) {
			response = processSubEventMessage(string);
		} else if (msgType.equals("event") && event.equals("subscribe") && !Strings.isNullOrEmpty(eventKey)) {
			response = process2DCodeEventMessage(string, "first");
		} else if (msgType.equals("event") && event.equals("SCAN")) {
			response = process2DCodeEventMessage(string, event);
		} else if (msgType.equals("event") && event.equals("LOCATION")) {
			response = processLocationEventMessage(string);
		} else if (msgType.equals("event") && event.equals("CLICK")) {
			response = processMenuEventMessage(string, event);
		} else if (msgType.equals("event") && event.equals("VIEW")) {
			response = processMenuEventMessage(string, event);
		} else if (msgType.equals("voice") && !Strings.isNullOrEmpty(recognition)) {
			response = processRecognitionMessage(string);
		}

		return response;
	}

	private String processRecognitionMessage(String string) {
		XMLProcessor<WxRecognitionMessage> processor = new XMLProcessor<WxRecognitionMessage>(WxRecognitionMessage.class);
		WxRecognitionMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a recognition message: " + msg.getMsgId());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String processMenuEventMessage(String string, String event) {
		XMLProcessor<WxMenuEventMessage> processor = new XMLProcessor<WxMenuEventMessage>(WxMenuEventMessage.class);
		WxMenuEventMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a menu event message: " + event + ", " + msg.getFromUserName() + "==>" + msg.getCreateTime());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String processLocationEventMessage(String string) {
		XMLProcessor<WxLocationEventMessage> processor = new XMLProcessor<WxLocationEventMessage>(WxLocationEventMessage.class);
		WxLocationEventMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a location event message: " + msg.getFromUserName() + "==>" + msg.getCreateTime());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String process2DCodeEventMessage(String string, String event) {
		XMLProcessor<Wx2DCodeEventMessage> processor = new XMLProcessor<Wx2DCodeEventMessage>(Wx2DCodeEventMessage.class);
		Wx2DCodeEventMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a 2D code scanning event message: " + event + ", " + msg.getFromUserName() + "==>" + msg.getCreateTime());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String processSubEventMessage(String string) {
		XMLProcessor<WxSubEventMessage> processor = new XMLProcessor<WxSubEventMessage>(WxSubEventMessage.class);
		WxSubEventMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a subscribe/unsubscribe event message: " + msg.getFromUserName() + "==>" + msg.getCreateTime());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String processLinkMessage(String string) {
		XMLProcessor<WxLinkMessage> processor = new XMLProcessor<WxLinkMessage>(WxLinkMessage.class);
		WxLinkMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a link message: " + msg.getMsgId());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String processLocationMessage(String string) {
		XMLProcessor<WxLocationMessage> processor = new XMLProcessor<WxLocationMessage>(WxLocationMessage.class);
		WxLocationMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a location message: " + msg.getMsgId());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String processVoiceMessage(String string) {
		XMLProcessor<WxVoiceMessage> processor = new XMLProcessor<WxVoiceMessage>(WxVoiceMessage.class);
		WxVoiceMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a voice message: " + msg.getMsgId());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String processVideoMessage(String string) {
		XMLProcessor<WxVideoMessage> processor = new XMLProcessor<WxVideoMessage>(WxVideoMessage.class);
		WxVideoMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a video message: " + msg.getMsgId());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String processImageMessage(String string) {
		XMLProcessor<WxImageMessage> processor = new XMLProcessor<WxImageMessage>(WxImageMessage.class);
		WxImageMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a image message: " + msg.getMsgId());

		// TODO
		WxTextResMessage resMsg = WxResMessageBuilder.buildTextResMessage();
		XMLProcessor<WxTextResMessage> processor1 = new XMLProcessor<WxTextResMessage>(WxTextResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	private String processTextMessage(String string) {
		XMLProcessor<WxTextMessage> processor = new XMLProcessor<WxTextMessage>(WxTextMessage.class);
		WxTextMessage msg = processor.xmlToObject(string);
		LOGGER.info("Received a text message: " + msg.getMsgId());

		// TODO
		WxNewsResMessage resMsg = WxResMessageBuilder.buildNewsResMessage();
		XMLProcessor<WxNewsResMessage> processor1 = new XMLProcessor<WxNewsResMessage>(WxNewsResMessage.class);
		String response = processor1.objectToXML(resMsg);
		return response;
	}

	public void sendCustomer(Object msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to send to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_SEND, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastCustomerAllNews(WxNewsAllBroadcastMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to send to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_BROADCAST_ALL, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastCustomerGroupNews(WxNewsGroupBroadcastMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to broadcast to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_BROADCAST_GROUP, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastCustomerGroupText(WxTextGroupBroadcastMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to broadcast to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_BROADCAST_GROUP, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastCustomerGroupVoice(WxVoiceGroupBroadcastMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to broadcast to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_BROADCAST_GROUP, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastCustomerGroupImage(WxImageGroupBroadcastMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to broadcast to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_BROADCAST_GROUP, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastCustomerOpenidNews(WxNewsOpenidBroadcastMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to broadcast to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_BROADCAST_OPENID, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastCustomerOpenidText(WxTextOpenidBroadcastMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to broadcast to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_BROADCAST_OPENID, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastCustomerOpenidVoice(WxVoiceOpenidBroadcastMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to broadcast to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_BROADCAST_OPENID, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastCustomerOpenidImage(WxImageOpenidBroadcastMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("JSON to broadcast to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_BROADCAST_OPENID, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendTemplateMessage(WxTemplateMessage msg) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(msg);
			LOGGER.info("Template message to send to customer: " + json);

			serviceService.post(NetworkService.CUSTOMER_SEND_TEMPLATE, json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
