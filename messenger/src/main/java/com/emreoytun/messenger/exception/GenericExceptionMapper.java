package com.emreoytun.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.emreoytun.messenger.messages.ErrorMessage;

//@Provider // For JAX-RS to scan this class in runtime, this class should be annotated/registered as using @Provider. 
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable e) {
		ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), 404, "http://emreoytun.org");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.build();
	}

}
