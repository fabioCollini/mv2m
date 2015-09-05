package it.cosenonjaviste.model;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface NoteSaverService {
    @POST("/save") @FormUrlEncoded Response save(@Field("id") long id, @Field("title") String title, @Field("text") String text);
}
