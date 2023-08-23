package com.rbts.hrms.authentication.exception;

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



	@ExceptionHandler(DuplicateDataException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleDuplicateDataException(final DuplicateDataException exception,
																		final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}


	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleContraintViolation(final ConstraintViolationException exception,
																	final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}


	@ExceptionHandler(TokenRefreshException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ExceptionResponse handleTokenRefreshException(final TokenRefreshException exception,
																	final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}


}
