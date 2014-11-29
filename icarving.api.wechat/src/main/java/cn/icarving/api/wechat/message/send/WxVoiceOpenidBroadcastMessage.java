package cn.icarving.api.wechat.message.send;

public class WxVoiceOpenidBroadcastMessage {
	private String[] touser;
	private Voice voice;
	private String msgtype;

	public String[] getTouser() {
		return touser;
	}

	public void setTouser(String[] touser) {
		this.touser = touser;
	}

	public Voice getMpnews() {
		return voice;
	}

	public void setMpnews(Voice mpnews) {
		this.voice = mpnews;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public class Voice {
		private String media_id;

		public String getMedia_id() {
			return media_id;
		}

		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}

	}
}
