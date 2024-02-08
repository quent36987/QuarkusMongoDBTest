package com.epita.utils.errors;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ThrowableErrorMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(final Throwable exception) {

        System.out.println(exception.getMessage());

        if (exception instanceof ErrorCode)
            return Response.status(((ErrorCode) exception).getCode()).entity(exception.getMessage()).build();

        return Response.status(500).build();
    }
}