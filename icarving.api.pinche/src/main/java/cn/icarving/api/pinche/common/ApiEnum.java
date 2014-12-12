package cn.icarving.api.pinche.common;

public enum ApiEnum {

	API_SUCCESS("1000000", "Api operation success"),
	
	USER_REGISTER_FAILED_USER_ALREADY_REGISTERED("1000001", "User already registered"),
	
	USER_LOGIN_FAILED_CANNOT_FIND_USER("1000002", "Cannot find user"),
	USER_LOGIN_FAILED_PASSWORD_NOT_MATCH("1000003", "Password not match"),
	
	USER_LOGOFF_FAILED_CANNOT_FIND_USER("1000004", "Cannot find user"),
	USER_LOGOFF_FAILED_PASSWORD_NOT_MATCH("1000005", "Password not match"),
	
	USER_UPDATE_FAILED_CANNOT_FIND_USER("1000006", "Cannot find user"),
	USER_UPDATE_FAILED_PASSWORD_NOT_MATCH("1000007", "Password not match"),
	
	ACTIVITY_UPDATE_FAILED_CANNOT_FIND_USER("1000006", "Cannot find user"),
	
	ACTIVITY_CANCEL_FAILED_CANNOT_FIND_USER("1000006", "Cannot find user"),
	ACTIVITY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY("1000008", "Cannot find pick/picked activity"),
	
	APPLY_CREATE_FAILED_CANNOT_FIND_PICK_ACTIVITY("1000008", "Cannot find pick/picked activity"),
	APPLY_CREATE_FAILED_INVALID_ACTIVITY("1000008", "Invalid pick/picked activity"),
	
	APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY("1000009", "Cannot find pick/picked activity"),
	APPLY_APPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY("1000010", "Cannot find pick/picked activity apply"),
	
	APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY("1000011", "Cannot find pick/picked activity"),
	APPLY_UNAPPROVE_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY("1000012", "Cannot find pick/picked activity apply"),
	
	APPLY_FIND_FAILED_CANNOT_FIND_USER("1000013", "Cannot find user"),
	
	APPLY_CANCEL_FAILED_CANNOT_FIND_USER("1000014", "Cannot find user"),
	APPLY_CANCEL_FAILED_CANNOT_FIND_PICK_ACTIVITY_APPLY("1000015", "Cannot find pick/picked activity apply"),
	APPLY_CANCEL_FAILED_NOT_OWNER_OF_PICK_ACTIVITY_APPLY("1000016", "User is not owner of pick/picked activity");

	private String code;
	private String message;

	private ApiEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

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

}
