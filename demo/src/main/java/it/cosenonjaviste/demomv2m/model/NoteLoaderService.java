package it.cosenonjaviste.demomv2m.model;

import java.util.List;

import retrofit.http.GET;

public interface NoteLoaderService {
    @GET("/data.json") Note load();

    List<Note> loadItems();
}