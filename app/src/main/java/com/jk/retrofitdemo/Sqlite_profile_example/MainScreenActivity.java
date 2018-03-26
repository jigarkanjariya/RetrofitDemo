package com.jk.retrofitdemo.Sqlite_profile_example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jk.retrofitdemo.R;
import com.jk.retrofitdemo.Sqlite_profile_example.database.DatabaseHandler;
import com.jk.retrofitdemo.Sqlite_profile_example.shaaredprefs.SharedPref;

import static com.jk.retrofitdemo.Sqlite_profile_example.shaaredprefs.SharedPref.MyPREFERENCES;

public class MainScreenActivity extends AppCompatActivity {

    EditText mEtName;
    EditText mEtPassword;
    Button mBtnLogin;
    Button mBtnSignup;
    TextView mTvLogo;
    private DatabaseHandler databaseHandler;
    public static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mEtName = findViewById(R.id.et_name);
        mEtPassword = findViewById(R.id.et_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnSignup = findViewById(R.id.btn_signup);
        mTvLogo = findViewById(R.id.tv_logo);

        databaseHandler = new DatabaseHandler(this);
        final SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String id2 = SharedPref.getStringData(MainScreenActivity.this,KEY_ID);
        if(!id2.equals("")){
            Intent intent = new Intent(MainScreenActivity.this, NavigationActivity.class);
            startActivity(intent);
            finish();
        }
        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainScreenActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    String val = mEtName.getText().toString();
                    String id = databaseHandler.getLoginData(mEtName.getText().toString(), mEtPassword.getText().toString());
                    String val2 = databaseHandler.getimage(id);

                    if (!id.equals("")) {
                        SharedPref.saveSrtingData(MainScreenActivity.this, KEY_ID, id);
                        Intent intent = new Intent(MainScreenActivity.this, NavigationActivity.class);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name", val);
                        editor.putString("picture", val2);
                        editor.commit();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainScreenActivity.this, "please fill correct data", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public boolean checkValidation() {

        if (mEtName.getText().toString().equals("")) {
            Toast.makeText(MainScreenActivity.this, "please enter username", Toast.LENGTH_LONG).show();
            return false;
        } else if (mEtPassword.getText().toString().equals("")) {
            Toast.makeText(MainScreenActivity.this, "please enter password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
