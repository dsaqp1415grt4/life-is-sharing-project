package edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.upc.eetac.dsa.dsaqp1415grt4.lifeissharing.api.model.LifeissharingError;


@Provider
public class WebApplicationExceptionMapper implements
		ExceptionMapper <WebApplicationException> {
	@Override
	public Response toResponse(WebApplicationException exception) {
		LifeissharingError error = new LifeissharingError(
				exception.getResponse().getStatus(), exception.getMessage());
		return Response.status(error.getStatus()).entity(error).type(MediaType.LIFE_API_ERROR).build();
	}

}