package it.cosenonjaviste.demomv2m.model;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface NoteSaverService {
    @POST("/Note/") SaveResponse createNewNote(@Body Note note);

    @PUT("/Note/{objectId}") Response save(@Path("objectId") String objectId, @Body Note note);
}
