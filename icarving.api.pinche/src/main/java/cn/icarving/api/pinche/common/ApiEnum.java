package cn.icarving.api.pinche.common;

public enum ApiEnum {

	API_SUCCESS("1000000", "Api operation success", "操作成功"),
	
	USER_ALREADY_REGISTERED("1000001", "User already registered", "该用户已经注册"),	
	USER_CANNOT_FIND("1000002", "Cannot find user", "找不到该用户，请重试"),
	USER_PASSWORD_NOT_MATCH("1000003", "Password not match", "密码不对，请重试"),

	ACTIVITY_CANNOT_FIND("1000008", "Cannot find activity", "找不到活动"),
	ACTIVITY_ALREADY_CANCELLED("1000008", "Cannot find activity", "该活动已取消"),

	APPLY_ACTIVITY_INVALID("1000008", "Invalid activity", "该活动已经无法申请"),
	APPLY_ALREADY_APPLIED_ACTIVITY("1000008", "Already applied activity", "您已申请过该活动"),
	APPLY_CANNOT_FIND("1000010", "Cannot find activity apply", "找不到活动申请"),
	APPLY_ALREADY_APPROVED("1000015", "Already approved activity apply", "该申请已批准"),
	APPLY_ALREADY_UNAPPROVED("1000015", "Already unapproved activity apply", "该申请已拒绝"),
	APPLY_ALREADY_CANCELLED("1000015", "Already cancelled activity apply", "该申请已被取消"),
	
	MESSAGE_CANNOT_FIND("1000008", "Cannot find user message", "该消息不存在"),
	MESSAGE_ALREADY_READ("1000008", "Already read this message", "该消息已阅读"),
	
	SEARCH_ADDRESS_CANNOT_EMPTY("1000008", "Source address cannot be empty", "出发地不能为空");

	private String code;
	private String message;
	private String cnMessage;

	private ApiEnum(String code, String message, String cnMessage) {
		this.code = code;
		this.message = message;
		this.cnMessage = cnMessage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return cnMessage;
	}

	public void setMessage(String message) {
		this.cnMessage = message;
	}

}
