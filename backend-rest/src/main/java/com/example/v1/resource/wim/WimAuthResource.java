package com.example.v1.resource.wim;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.example.v1.resource.Api;
import com.example.v1.resource.auth.DefaultIsLoggedInResource;
import io.quarkus.security.Authenticated;


@Path(Api.VERSION + Api.WIM_ROLE)
@Authenticated
public class WimAuthResource implements DefaultIsLoggedInResource
{
	@Path("/auth/login")
	@Operation(operationId = "wimLogin")
	@GET
	@Produces("text/plain")
	public String login()
	{
		return "YOU_SHOULD_BE_REDIRECTED";
	}
}
