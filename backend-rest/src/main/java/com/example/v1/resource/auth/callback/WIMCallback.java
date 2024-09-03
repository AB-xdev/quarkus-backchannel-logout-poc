package com.example.v1.resource.auth.callback;

import java.net.URI;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import com.example.v1.resource.Api;
import io.quarkus.security.Authenticated;


@Path(Api.VERSION + "/auth/wim/callback")
@Authenticated
public class WIMCallback
{
	@GET
	public Response handle()
	{
		return Response.ok("YOU_ARE_LOGGED_IN")
			.build();
	}
}
