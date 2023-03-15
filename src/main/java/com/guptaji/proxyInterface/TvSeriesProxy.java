package com.guptaji.proxyInterface;

import com.guptaji.entity.TvSeries;
import io.vertx.core.json.JsonArray;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;


@RegisterRestClient(baseUri = "https://api.tvmaze.com")
@Path("/")
public interface TvSeriesProxy {

    // URL we wanna hit is https://api.tvmaze.com/shows/169 base url + /shows + path param
    // right now this proxy interface if ready for all the url's for which base url is 'https://api.tvmaze.com'

    @GET
    @Path("shows/{id}")
    TvSeries getTvSeriesById(@PathParam("id") int id);

    // url we wanna hit here is https://api.tvmaze.com/search/people?q=lauren

    @GET
    @Path("search/people")
    JsonArray getTvSeriesDataByName(@QueryParam("q") String personName);
}
