package com.jk.retrofitdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jk.retrofitdemo.Retrofit_Example2.Retrofit_HashmapActivity;
import com.jk.retrofitdemo.Sqlite_profile_example.SplashScreenActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button mBtnRetro = findViewById(R.id.btn_retro);
        Button mBtnSql = findViewById(R.id.btn_sql_profile);

        mBtnRetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, Retrofit_HashmapActivity.class);
                startActivity(intent);
            }
        });
        mBtnSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, SplashScreenActivity.class);
                startActivity(intent);
            }
        });

    }
}
