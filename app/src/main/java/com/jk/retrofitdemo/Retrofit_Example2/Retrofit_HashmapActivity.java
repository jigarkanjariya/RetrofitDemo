package com.jk.retrofitdemo.Retrofit_Example2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.jk.retrofitdemo.R;
import com.jk.retrofitdemo.Retrofit_Example1.API;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_HashmapActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private ProgressBar mPbLoad;
    private ArrayList<ImgPojo> list;
    private ImgAdapter mAdapter;
    private static final String TAG = "Retrofit_HashmapActivit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit__hashmap);

        mRecyclerview = findViewById(R.id.recyclerview);

        Retrofit.Builder builder = new Retrofit.Builder();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(1000, TimeUnit.SECONDS)
//                .readTimeout(1000,TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create(new Gson())).build();

//       // Build retrofit object
//        Retrofit retrofit = builder.build();

        //create api
        final API2 api2 = (API2) retrofit.create(API2.class);

        Call<ImgPojo> postCall = api2.getdata();


        postCall.enqueue(new Callback<ImgPojo>() {
            @Override
            public void onResponse(Call<ImgPojo> call, Response<ImgPojo> response) {

                ArrayList<ImgPojo.DataBean.BasketsBean> body = (ArrayList<ImgPojo.DataBean.BasketsBean>) response.body().getData().getBaskets();
                Log.d(TAG,"data is:"+body);
                RecyclerView.LayoutManager  layoutManager=new LinearLayoutManager(getApplicationContext());
                mRecyclerview.setLayoutManager(layoutManager);
                mAdapter = new ImgAdapter(body,Retrofit_HashmapActivity.this);
                mRecyclerview.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<ImgPojo> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }
}
