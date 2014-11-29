package cn.icarving.api.wechat.message.send;

public class WxNewsGroupBroadcastMessage {
	private Filter filter;
	private MpNews mpnews;
	private String msgtype;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
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

	public class Filter {
		private String group_id;

		public String getGroup_id() {
			return group_id;
		}

		public void setGroup_id(String group_id) {
			this.group_id = group_id;
		}

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
