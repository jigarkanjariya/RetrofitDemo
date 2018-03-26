package com.jk.retrofitdemo.Retrofit_Example1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jk.retrofitdemo.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentActivity extends AppCompatActivity {

    RecyclerView mRecyclerComment;
    private ArrayList<CommentPojo> listcomment;
    private ArrayList<PostPojo> list;
    private CommentAdapter mAdapter;
    private static final String TAG = "CommentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

       mRecyclerComment = findViewById(R.id.recycler_comment);
        Bundle extra = getIntent().getExtras();
        int id = extra.getInt("UserId");
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create());

        //Build retrofit object
        Retrofit retrofit = builder.build();

        //create api
        final API api = retrofit.create(API.class);

        Call<ArrayList<CommentPojo>> postCall = api.getcomment(id);
        postCall.enqueue(new Callback<ArrayList<CommentPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentPojo>> call, Response<ArrayList<CommentPojo>> response) {
                ArrayList<CommentPojo> body = response.body();
                listcomment = body;
                RecyclerView.LayoutManager  layoutManager=new LinearLayoutManager(getApplicationContext());
                mRecyclerComment.setLayoutManager(layoutManager);
                mAdapter = new CommentAdapter(listcomment);
                mRecyclerComment.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<CommentPojo>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });


    }
}
