package cn.icarving.api.wechat.message.send;

import java.util.ArrayList;
import java.util.List;

public class WxResMessageBuilder {

	public static WxImageResMessage buildImageResMessage() {
		WxImageResMessage msg = new WxImageResMessage();
		msg.setCreateTime("1323213213");
		msg.setFromUserName("FromUserName");
		Image image = new Image();
		image.setMediaId("mediaId");
		msg.setImage(image);
		msg.setMsgType("image");
		msg.setToUserName("ToUserName");
		return msg;
	}

	public static WxMusicResMessage buildMusicResMessage() {
		WxMusicResMessage msg = new WxMusicResMessage();
		msg.setCreateTime("1323213213");
		msg.setFromUserName("FromUserName");
		Music music = new Music();
		music.setDescription("description");
		music.setHQMusicUrl("hqmusicurl");
		music.setMusicUrl("musicurl");
		music.setThumbMediaId("thumb");
		music.setTitle("title");
		msg.setMusic(music);
		msg.setMsgType("music");
		msg.setToUserName("ToUserName");
		return msg;
	}

	public static WxNewsResMessage buildNewsResMessage() {
		WxNewsResMessage msg = new WxNewsResMessage();
		msg.setCreateTime("1323213213");
		msg.setFromUserName("FromUserName");
		Item item = new Item();
		item.setDescription("description");
		item.setPicUrl("picUrl");
		item.setTitle("title");
		item.setUrl("url");
		List<Item> Articles = new ArrayList<Item>();
		Articles.add(item);
		msg.setArticles(Articles);
		msg.setMsgType("news");
		msg.setToUserName("ToUserName");
		return msg;
	}

	public static WxTextResMessage buildTextResMessage() {
		WxTextResMessage msg = new WxTextResMessage();
		msg.setCreateTime("1323213213");
		msg.setFromUserName("FromUserName");
		msg.setContent("content");
		msg.setMsgType("text");
		msg.setToUserName("ToUserName");
		return msg;
	}

	public static WxVideoResMessage buildVideoResMessage() {
		WxVideoResMessage msg = new WxVideoResMessage();
		msg.setCreateTime("1323213213");
		msg.setFromUserName("FromUserName");
		Video Video = new Video();
		Video.setMediaId("mediaId");
		Video.setTitle("title");
		Video.setDescription("description");
		msg.setVideo(Video);
		msg.setMsgType("video");
		msg.setToUserName("ToUserName");
		return msg;
	}

	public static WxVoiceResMessage buildVoiceResMessage() {
		WxVoiceResMessage msg = new WxVoiceResMessage();
		msg.setCreateTime("1323213213");
		msg.setFromUserName("FromUserName");
		Voice Voice = new Voice();
		Voice.setMediaId("mediaId");
		msg.setVoice(Voice);
		msg.setMsgType("voice");
		msg.setToUserName("ToUserName");
		return msg;
	}

}
