package cn.icarving.api.wechat.message.user;

public class UpdateGroupByUserRequest {
	private String openid;
	private String to_groupid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getTo_groupid() {
		return to_groupid;
	}

	public void setTo_groupid(String to_groupid) {
		this.to_groupid = to_groupid;
	}

}
