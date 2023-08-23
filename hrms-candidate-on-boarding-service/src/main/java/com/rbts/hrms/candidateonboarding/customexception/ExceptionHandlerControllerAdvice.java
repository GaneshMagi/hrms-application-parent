package com.rbts.hrms.candidateonboarding.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

//	@ExceptionHandler(ResourceNotFoundException.class)
//	@ResponseStatus(value = HttpStatus.NOT_FOUND)
//	public @ResponseBody ExceptionResponse handleResourceNotFound(final ResourceNotFoundException exception,
//																  final HttpServletRequest request) {
//
//		ExceptionResponse error = new ExceptionResponse();
//		error.setErrorMessage(exception.getMessage());
//		return error;
//	}

//	@ExceptionHandler(ResourceNotFoundException.class)
//	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//	public @ResponseBody ExceptionResponse handleException(final ResourceNotFoundException exception,
//			final HttpServletRequest request) {
//
//		ExceptionResponse error = new ExceptionResponse();
//		error.setErrorMessage(exception.getMessage());
//		return error;
//	}




	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
   public @ResponseBody ExceptionResponse handleResourceNotFound(final ResourceNotFoundException exception,
															  final HttpServletRequest request) {

	ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}


	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleResourceNotFound1(final DataNotFoundException exception,
																  final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}


	@ExceptionHandler(DataIntegrityException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleResourceNotFound2(final DataIntegrityException exception,
																   final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}


	@ExceptionHandler(DataalreadyexistsException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleResourceNotFound3(final DataalreadyexistsException exception,
																   final HttpServletRequest request) {
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}

}
