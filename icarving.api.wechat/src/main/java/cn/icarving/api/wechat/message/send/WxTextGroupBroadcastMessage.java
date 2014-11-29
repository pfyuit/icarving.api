package cn.icarving.api.wechat.message.send;

public class WxTextGroupBroadcastMessage {

	private Filter filter;
	private Text text;
	private String msgtype;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
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

	public class Filter {
		private String group_id;

		public String getGroup_id() {
			return group_id;
		}

		public void setGroup_id(String group_id) {
			this.group_id = group_id;
		}

	}

	public class Text {
		private String content;

		public String getContent() {
			return content;
		}

		public void setCotent(String content) {
			this.content = content;
		}

	}

}
