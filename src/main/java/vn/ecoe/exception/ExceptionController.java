package vn.ecoe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.ecoe.response.ErrorResponseModel;
import vn.ecoe.response.ResponseModel;

import java.util.UUID;

@ControllerAdvice
public class ExceptionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseModel> validateChecklistName(ResourceNotFoundException e) {
		String traceId = UUID.randomUUID().toString();
		LOGGER.info(this.getErrorInfo(traceId, e));
		ResponseModel error = new ErrorResponseModel(traceId, HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
	}

	@ExceptionHandler(AccessDeniedOnCaseException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ResponseEntity<ResponseModel> handleAccessDeniedOnCaseException(AccessDeniedOnCaseException e) {
		String traceId = UUID.randomUUID().toString();
		LOGGER.error(this.getErrorInfo(traceId, e));
		
		ResponseModel error = new ErrorResponseModel(traceId, HttpStatus.CONFLICT.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(error);
	}
	
	private String getErrorInfo(String traceId, Exception e) {		
		String message = "TraceId = [" +traceId+"] : " +  e.getMessage();
		return message;
	}

}
