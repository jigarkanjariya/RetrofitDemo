package com.jk.retrofitdemo.Retrofit_Example2;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Abc on 3/5/2018.
 */

public interface API2 {

    @POST("v2/5a9d201f3100004a1dab5265")
    Call<ImgPojo> getdata();

}
