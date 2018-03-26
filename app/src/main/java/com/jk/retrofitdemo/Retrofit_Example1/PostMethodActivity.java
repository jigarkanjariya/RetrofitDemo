package com.jk.retrofitdemo.Retrofit_Example1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jk.retrofitdemo.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostMethodActivity extends AppCompatActivity {

    private static final String TAG = "PostMethodActivity";

    EditText mEtTitle;
    EditText mEtBody;
    Button mBtnSubmit;
    PostAdapter mAdapter;
    ProgressBar mPbPost;
    private ArrayList<PostPojo> list = new ArrayList<>();
    PostPojo postPojo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_method);

        mEtTitle = findViewById(R.id.et_title);
        mEtBody = findViewById(R.id.et_body);
        mBtnSubmit = findViewById(R.id.btn_submit);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit.Builder builder = new Retrofit.Builder();
                builder.baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create());
                //Build retrofit object
                Retrofit retrofit = builder.build();

                //create api
                API api = retrofit.create(API.class);

                Call<ArrayList<PostPojo>> postCall = api.putpost(postPojo);

                postCall.enqueue(new Callback<ArrayList<PostPojo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PostPojo>> call, Response<ArrayList<PostPojo>> response) {
                        ArrayList<PostPojo> body = response.body();
                        Log.d(TAG,"submit post is - "+body);
                        Toast.makeText(PostMethodActivity.this,"Success",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<ArrayList<PostPojo>> call, Throwable t) {
                        Log.d(TAG,"error"+t);
                    }
                });

            }
        });





    }
}
