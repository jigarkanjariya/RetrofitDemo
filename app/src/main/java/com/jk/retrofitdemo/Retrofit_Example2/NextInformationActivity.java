package com.jk.retrofitdemo.Retrofit_Example2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jk.retrofitdemo.R;

public class NextInformationActivity extends AppCompatActivity {

    TextView mTvInfoName;
    TextView mTvInfoIntro;
    ImageView mIvInfoImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_information);

        mTvInfoName = findViewById(R.id.tv_infoname);
        mTvInfoIntro = findViewById(R.id.tv_infointro);
        mIvInfoImg = findViewById(R.id.iv_infoimg);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String intro = bundle.getString("intro");
        String image= bundle.getString("image");

        mTvInfoName.setText(name);
        mTvInfoIntro.setText(intro);
        Glide.with(this).load(image).into(mIvInfoImg);
    }
}
