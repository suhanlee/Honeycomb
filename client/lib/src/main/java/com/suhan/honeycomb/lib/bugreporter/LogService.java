package com.suhan.honeycomb.lib.bugreporter;

import com.suhan.honeycomb.lib.bugreporter.model.Message;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by suhanlee on 15. 7. 25..
 */
public interface LogService {
    @GET("/logs.json")
    List<Message> listLog();

    @POST("/logs.json")
    void postLog(@Body Message log, Callback<Message> cb);
}
