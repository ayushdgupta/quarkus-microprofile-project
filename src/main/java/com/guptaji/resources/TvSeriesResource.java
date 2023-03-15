package com.guptaji.resources;

import com.guptaji.proxyInterface.TvSeriesProxy;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logmanager.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;


@Path("/tvseries")
public class TvSeriesResource {

    // here we will create / expose two endpoints where both these endpoints will call some other rest
    // endpoints / API's to fetch the data and show it to our end user.

    public static final Logger LOGGER = Logger.getLogger(String.valueOf(TvSeriesResource.class));

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

    // API to test the other fault tolerance annotations like @Retry, @Timeout, @CircuitBreaker
    // This API will return the no. of TV Series running in a country
    @GET
    @Path("/faultToleranceTesting/{countryName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response tvSeriesByCountry(@PathParam("countryName") String country) throws InterruptedException {
        LOGGER.info("tvSeriesByCountry API Called for country = " + country);

        // for the @timeout fault tolerance property we will let this API to give 20 sec late response.
        Thread.sleep(20000);

        return Response.ok("No. of tv series are running in country = " + country + " is " +
                new Random().nextInt(25)).build();
    }
}
