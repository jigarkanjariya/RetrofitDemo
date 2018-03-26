package com.jk.retrofitdemo.Retrofit_Example1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jk.retrofitdemo.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView mTvUserid;
    TextView mTvBody;
    TextView mTvTitle;
    RecyclerView mPostRecycler;
    PostAdapter mAdapter;
    ProgressBar mPbPost;
    private ArrayList<PostPojo> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvUserid = findViewById(R.id.tv_userid);
        mTvBody = findViewById(R.id.tv_body);
        mTvTitle = findViewById(R.id.tv_title);
        mPostRecycler = findViewById(R.id.post_recyclerview);
       // mPbPost.setVisibility(View.VISIBLE);




        mPbPost = findViewById(R.id.pb_post);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create());

        //Build retrofit object
        Retrofit retrofit = builder.build();

        //create api
        final API api = retrofit.create(API.class);

        Call<ArrayList<PostPojo>> postCall = api.getpost(String.valueOf(1));

        postCall.enqueue(new Callback<ArrayList<PostPojo>>() {
            @Override
            public void onResponse(Call<ArrayList<PostPojo>> call, Response<ArrayList<PostPojo>> response) {
                ArrayList<PostPojo> body = response.body();
                list = body;
                Log.i(TAG, "onResponse: " + new Gson().toJson(body));
                RecyclerView.LayoutManager  layoutManager=new LinearLayoutManager(getApplicationContext());
                mPostRecycler.setLayoutManager(layoutManager);
                mAdapter = new PostAdapter(list);
                mAdapter= new PostAdapter(getApplicationContext(),list, new OnItemClicklistner() {
                    @Override
                    public void onItemClick(View v, int position) {

                        Toast.makeText(MainActivity.this, "clicked position:"+position, Toast.LENGTH_SHORT).show();
                        int value=list.get(position).getUserId();
                        Intent i = new Intent(MainActivity.this, CommentActivity.class);
                        i.putExtra("UserId", value);
                        startActivity(i);


                    }
                });
                mPostRecycler.setAdapter(mAdapter);

               // mPbPost.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onFailure(Call<ArrayList<PostPojo>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });



    }
}
