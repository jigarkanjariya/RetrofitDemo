package com.jk.retrofitdemo.Sqlite_profile_example;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jk.retrofitdemo.R;
import com.jk.retrofitdemo.Sqlite_profile_example.database.DatabaseHandler;
import com.jk.retrofitdemo.Sqlite_profile_example.model.UserProfile;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    ImageView mIvProfilepic;
    EditText mEtName;
    EditText mEtEmailid;
    EditText mEtPhoneno;
    EditText mEtPassword;
    Button mBtnRegister;
    RadioButton mRbMale;
    RadioButton mRbFemale;
    RadioGroup mRggender;
    TextView mTvGender;
    String value2;
    private static final int GALLERY_CODE = 2;
    private static final int CAPTURE_CODE = 1;
    private static final int MY_PERMISSIONS = 101;
    private static final String TAG = "SignUpActivity";
    String picpath;


    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        mBtnRegister = findViewById(R.id.btn_register);
        mEtEmailid = findViewById(R.id.et_email);
        mEtName = findViewById(R.id.et_name1);
        mEtPassword = findViewById(R.id.et_password1);
        mEtPhoneno = findViewById(R.id.et_phone);
        mIvProfilepic = findViewById(R.id.iv_profilepic);
        mRbMale = findViewById(R.id.rb_male);
        mRbFemale = findViewById(R.id.rb_female);
        mRggender = findViewById(R.id.rg_user);
        mTvGender = findViewById(R.id.tv_gender);

        if (ContextCompat.checkSelfPermission(SignUpActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(SignUpActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS);
        }


        databaseHandler = new DatabaseHandler(this);

       mIvProfilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectImage();
            }
        });

        mRggender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton value = group.findViewById(checkedId);
                 value2= value.getText().toString();

            }
        });



        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkvalidation() && mailvalidation() && isValidMobile()) {
                    databaseHandler.adduser(new UserProfile(mEtName.getText().toString(), mEtPassword.getText().toString(),
                            mEtEmailid.getText().toString(), mEtPhoneno.getText().toString(), value2,picpath));
                    Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public boolean checkvalidation() {

        if (mEtName.getText().toString().equals("")) {
            Toast.makeText(SignUpActivity.this, "please enter name", Toast.LENGTH_LONG).show();
            return false;
        } else if (mEtPhoneno.getText().toString().equals("") && isValidMobile()) {
            Toast.makeText(SignUpActivity.this, "please enter phoneno", Toast.LENGTH_LONG).show();
            return false;
        } else if (mEtPassword.getText().toString().equals("")) {
            Toast.makeText(SignUpActivity.this, "please enter password", Toast.LENGTH_LONG).show();
            return false;
        } else if (mEtEmailid.getText().toString().equals("")) {
            Toast.makeText(SignUpActivity.this, "please enter emailid", Toast.LENGTH_LONG).show();
            return false;
        }
        mRggender.getCheckedRadioButtonId();
        if (mRggender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "please select gender", Toast.LENGTH_LONG).show();
        } else {
            Log.d("QAOD", "gender is selected");
        }
        return true;
    }

    public boolean mailvalidation(){
        String aa= "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
        if(mEtEmailid.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
        {
            return true;
        }
        else{
            Toast.makeText(SignUpActivity.this,"invalid email address",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean isValidMobile() {
        String phone = mEtPhoneno.getText().toString();
       if(mEtPhoneno.getText().length()>=7 && mEtPhoneno.getText().length()<=13){
           return true;
       }
       else
           Toast.makeText(SignUpActivity.this,"invalid phone no",Toast.LENGTH_LONG).show();
           return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_CODE) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    mIvProfilepic.setImageBitmap(bitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == GALLERY_CODE) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.d(TAG,"image path is: "+picturePath);
                picpath = picturePath;
                mIvProfilepic.setImageBitmap(thumbnail);
            }
        }

    }
    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, CAPTURE_CODE);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_CODE);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}





