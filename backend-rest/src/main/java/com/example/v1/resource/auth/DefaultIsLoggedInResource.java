package com.example.v1.resource.auth;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


public interface DefaultIsLoggedInResource
{
	@Path("/isLoggedIn")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	default DefaultAuthStatus isLoggedIn()
	{
		return new DefaultAuthStatus();
	}
	
	record DefaultAuthStatus()
	{
	}
}
