package cn.icarving.api.wechat.message.send;


public class WxTextOpenidBroadcastMessage {
	private String[] touser;
	private Text text;
	private String msgtype;

	public String[] getTouser() {
		return touser;
	}

	public void setTouser(String[] touser) {
		this.touser = touser;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public class Text {
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

	}
}
