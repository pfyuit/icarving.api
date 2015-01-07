package cn.icarving.api.wechat.message.user;

public class WechatRegisterOrLoginResponse {
	private String code;
	private String message;
	private WechatRegisterOrLoginUserInfo response;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public WechatRegisterOrLoginUserInfo getResponse() {
		return response;
	}

	public void setResponse(WechatRegisterOrLoginUserInfo response) {
		this.response = response;
	}

	public class WechatRegisterOrLoginUserInfo {
		private int uid;
		private String name;
		private String phone;
		private String username;
		private String password;
		private String wechatOpenid;
		private String wechatUnionid;
		private int login;

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getWechatOpenid() {
			return wechatOpenid;
		}

		public void setWechatOpenid(String wechatOpenid) {
			this.wechatOpenid = wechatOpenid;
		}

		public String getWechatUnionid() {
			return wechatUnionid;
		}

		public void setWechatUnionid(String wechatUnionid) {
			this.wechatUnionid = wechatUnionid;
		}

		public int getLogin() {
			return login;
		}

		public void setLogin(int login) {
			this.login = login;
		}

	}

}
