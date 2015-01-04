package cn.icarving.api.pinche.common;

public enum ApiEnum {

	API_SUCCESS("1000000", "Api operation success", "操作成功"),
	
	USER_REGISTER_FAILED_USER_ALREADY_REGISTERED("1000001", "User already registered", "该用户已经注册"),
	
	USER_LOGIN_FAILED_CANNOT_FIND_USER("1000002", "Cannot find user", "找不到该用户，请重试"),
	USER_LOGIN_FAILED_PASSWORD_NOT_MATCH("1000003", "Password not match", "密码不匹配，请重试"),
	
	USER_LOGOFF_FAILED_CANNOT_FIND_USER("1000004", "Cannot find user", "找不到该用户，请重试"),
	USER_LOGOFF_FAILED_PASSWORD_NOT_MATCH("1000005", "Password not match", "密码不匹配，请重试"),
	
	USER_UPDATE_FAILED_CANNOT_FIND_USER("1000006", "Cannot find user", "找不到该用户，请重试"),
	USER_UPDATE_FAILED_PASSWORD_NOT_MATCH("1000007", "Password not match", "密码不匹配，请重试"),
	
	ACTIVITY_UPDATE_FAILED_CANNOT_FIND_USER("1000006", "Cannot find user", "找不到该用户，请重试"),
	
	ACTIVITY_CANCEL_FAILED_CANNOT_FIND_USER("1000006", "Cannot find user", "找不到该用户，请重试"),
	ACTIVITY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY("1000008", "Cannot find pick/picked activity", "找不到活动"),
	
	APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY("1000008", "Cannot find pick/picked activity", "找不到活动"),
	APPLY_CREATE_FAILED_INVALID_ACTIVITY("1000008", "Invalid pick/picked activity", "该活动已经无法申请"),
	
	APPLY_CREATE_FAILED_ALREADY_APPLIED_ACTIVITY("1000008", "Already applied pick/picked activity", "您已申请过该活动"),
	
	APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY("1000009", "Cannot find pick/picked activity", "找不到活动"),
	APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY("1000010", "Cannot find pick/picked activity apply", "找不到活动申请"),
	
	APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY("1000011", "Cannot find pick/picked activity", "找不到活动"),
	APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY("1000012", "Cannot find pick/picked activity apply", "找不到活动申请"),
	
	APPLY_FIND_FAILED_CANNOT_FIND_USER("1000013", "Cannot find user", "找不到该用户，请重试"),
	
	APPLY_CANCEL_FAILED_CANNOT_FIND_USER("1000014", "Cannot find user", "找不到该用户，请重试"),
	APPLY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY("1000015", "Cannot find pick/picked activity apply", "找不到该用户，请重试"),
	APPLY_CANCEL_FAILED_ALREADY_CANCELLED_APPLY("1000015", "Already cancelled pick/picked activity apply", "该申请已被取消"),
	APPLY_CANCEL_FAILED_NOT_OWNER_OF_PICK_ACTIVITY_APPLY("1000016", "User is not owner of pick/picked activity", "找不到活动申请"),
	
	MESSAGE_READ_FAILED_CANNOT_FIND_MESSAGE("1000008", "Cannot find user message", "该消息不存在"),
	MESSAGE_READ_FAILED_ALREADY_READ("1000008", "Already read this message", "该消息已阅读"),
	
	SEARCH_FAILED_SOURCE_ADDRESS_CANNOT_EMPTY("1000008", "Source address cannot be empty", "出发地不能为空");

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
