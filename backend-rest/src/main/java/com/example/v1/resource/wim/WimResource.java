package com.example.v1.resource.wim;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;

import com.example.v1.resource.Api;
import io.quarkus.oidc.IdToken;


@Path(Api.VERSION + Api.WIM_ROLE)
public class WimResource
{
	
	@IdToken
	JsonWebToken idToken;
	
	@Path("/welcome")
	@Operation(operationId = "welcome")
	@GET
	public String test()
	{
		return this.idToken.getName();
	}
}
