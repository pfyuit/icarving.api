package cn.icarving.api.pinche.common;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExcepitonHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ApiException.class })
	protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
		ApiException exception = (ApiException) ex;
		ApiExceptionWrapper wrapper = new ApiExceptionWrapper(exception.getCode(), exception.getMessage());
		ObjectMapper mapper = new ObjectMapper();
		String exceptionMessage = null;
		try {
			exceptionMessage = mapper.writeValueAsString(wrapper);
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		ResponseEntity<Object> result = handleExceptionInternal(ex, exceptionMessage, headers, httpStatus, request);
		return result;
	}

	public class ApiExceptionWrapper {
		private String code;
		private String message;

		public ApiExceptionWrapper(String code, String message) {
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

}
