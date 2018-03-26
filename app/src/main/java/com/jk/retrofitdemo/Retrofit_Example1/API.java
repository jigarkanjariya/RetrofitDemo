package com.jk.retrofitdemo.Retrofit_Example1;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Abc on 2/28/2018.
 */

public interface API {

    @GET("posts")
    Call<ArrayList<PostPojo>> getpost(@Query("userId") String userId);

    @GET("comments")
    Call<ArrayList<CommentPojo>> getcomment(@Query("postId") int postId);

    @POST("posts")
    Call<ArrayList<PostPojo>> putpost(@Body PostPojo postPojo);
}
