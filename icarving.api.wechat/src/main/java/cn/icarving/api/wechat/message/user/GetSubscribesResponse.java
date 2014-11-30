package cn.icarving.api.wechat.message.user;

import java.util.List;

public class GetSubscribesResponse {
	private int total;
	private int count;
	private Data data;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		private List<String> openid;
		private String next_openid;

		public List<String> getOpenid() {
			return openid;
		}

		public void setOpenid(List<String> openid) {
			this.openid = openid;
		}

		public String getNext_openid() {
			return next_openid;
		}

		public void setNext_openid(String next_openid) {
			this.next_openid = next_openid;
		}

	}

}
