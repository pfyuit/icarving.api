package cn.icarving.api.pinche.common;

public class ApiResponse {

	private String code;
	private String message;
	private Object response;

	public ApiResponse(String code, String message, Object response) {
		this.code = code;
		this.message = message;
		this.response = response;
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

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
