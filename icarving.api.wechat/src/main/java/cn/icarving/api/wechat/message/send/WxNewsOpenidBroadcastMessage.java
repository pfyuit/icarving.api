package cn.icarving.api.wechat.message.send;


public class WxNewsOpenidBroadcastMessage {
	private String[] touser;
	private MpNews mpnews;
	private String msgtype;

	public String[] getTouser() {
		return touser;
	}

	public void setTouser(String[] touser) {
		this.touser = touser;
	}

	public MpNews getMpnews() {
		return mpnews;
	}

	public void setMpnews(MpNews mpnews) {
		this.mpnews = mpnews;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public class MpNews {
		private String media_id;

		public String getMedia_id() {
			return media_id;
		}

		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}

	}
}
