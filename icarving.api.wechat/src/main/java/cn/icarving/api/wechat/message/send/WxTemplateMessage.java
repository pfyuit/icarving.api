package cn.icarving.api.wechat.message.send;

public class WxTemplateMessage {
	private String touser;
	private String template_id;
	private String url;
	private String topcolor;
	private Data data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data {
		private First first;
		private KeyNote1 kenote1;
		private KeyNote2 keynote2;
		private KeyNote3 keynote3;
		private Remark remark;

		public First getFirst() {
			return first;
		}

		public void setFirst(First first) {
			this.first = first;
		}

		public KeyNote1 getKenote1() {
			return kenote1;
		}

		public void setKenote1(KeyNote1 kenote1) {
			this.kenote1 = kenote1;
		}

		public KeyNote2 getKeynote2() {
			return keynote2;
		}

		public void setKeynote2(KeyNote2 keynote2) {
			this.keynote2 = keynote2;
		}

		public KeyNote3 getKeynote3() {
			return keynote3;
		}

		public void setKeynote3(KeyNote3 keynote3) {
			this.keynote3 = keynote3;
		}

		public Remark getRemark() {
			return remark;
		}

		public void setRemark(Remark remark) {
			this.remark = remark;
		}

	}

	public class First {
		private String value;
		private String color;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}

	public class KeyNote1 {
		private String value;
		private String color;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}

	public class KeyNote2 {
		private String value;
		private String color;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}

	public class KeyNote3 {
		private String value;
		private String color;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}

	public class Remark {
		private String value;
		private String color;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}

}
