package org.acme.rest.client;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@RegisterClientHeaders(DownstreamHeader.class)
@RegisterRestClient(configKey = "rapid-api")
public interface RapidApiService {

    @GET
    @Path("/get")
    String getByPredictionId(@HeaderParam("name-header") final String headerValue,
                             @QueryParam("foo") final String foo,
                             @QueryParam("bar") final String bar);
}
