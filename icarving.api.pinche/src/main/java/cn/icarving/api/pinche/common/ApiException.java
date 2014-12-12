package cn.icarving.api.pinche.common;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 3985874829107065907L;
	private String code;
	private String message;

	public ApiException(String code, String message) {
		super();
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
