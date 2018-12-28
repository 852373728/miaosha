package com.sun.exception;

import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.result.CodeMsg;
import com.sun.result.Result;

@ControllerAdvice
@ResponseBody
public class HandleException {

	@ExceptionHandler(value=Exception.class)
	public Result<String> handleException(Exception ex){
		ex.printStackTrace();
		if(ex instanceof MyselfException) {
			MyselfException mex=(MyselfException) ex;
			return Result.error(mex.getCodeMsg());
		}else if(ex instanceof BindException) {
			BindException bex=(BindException) ex;
			List<ObjectError> allErrors = bex.getAllErrors();
			ObjectError error = allErrors.get(0);
			String defaultMessage = error.getDefaultMessage();
			return Result.error(CodeMsg.ERROR_STRFORMAT.format(defaultMessage));
		}else {
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
	
}
