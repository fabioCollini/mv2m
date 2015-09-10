package it.cosenonjaviste.demomv2m.model;

import retrofit.http.GET;

public interface NoteLoaderService {
    @GET("/data.json") Note load();
}