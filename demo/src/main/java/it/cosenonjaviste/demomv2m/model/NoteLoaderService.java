package it.cosenonjaviste.demomv2m.model;

import retrofit.http.GET;
import retrofit.http.Path;

public interface NoteLoaderService {

    @GET("/Note/{objectId}") Note load(@Path("objectId") String objectId);

    @GET("/Note") NoteListResponse loadItems();
}