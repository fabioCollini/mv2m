package it.cosenonjaviste.model;

import retrofit.http.GET;

public interface NoteLoaderService {
    @GET("/data.json") Note load();
}