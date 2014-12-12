package cn.icarving.api.pinche.common;

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
		String exceptionMessage = ex.getMessage();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return handleExceptionInternal(ex, exceptionMessage, new HttpHeaders(), httpStatus, request);
	}

}
