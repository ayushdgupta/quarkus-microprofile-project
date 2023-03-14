package com.guptaji.resources;

import com.guptaji.proxyInterface.TvSeriesProxy;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tvseries")
public class TvSeriesResource {

    // here we will create / expose two endpoints where both these endpoints will call some other rest
    // endpoints / API's to fetch the data and show it to our end user.

    // import proxyInterface which can call our TV API's
    @RestClient
    TvSeriesProxy tvSeriesProxy;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTvSeriesById(@PathParam("id") int id){
        return Response.ok(tvSeriesProxy.getTvSeriesById(id)).build();
    }

    // fallback method of below API but when i created this method in a separate class it did n't work?
    // to test this fallback is executing or not just change the API path from 'search/people' to 'search/people11'
    public Response getTvSeriesDataByPersonNameByFallback(String personName){
        return Response.ok("Site is under Maintenance").build();
    }

    @GET
    @Fallback(fallbackMethod = "getTvSeriesDataByPersonNameByFallback")
    @Path("/person/{personName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTvSeriesDataByPersonName(@PathParam("personName") String personName){
        return Response.ok(tvSeriesProxy.getTvSeriesDataByName(personName)).build();
    }

}
