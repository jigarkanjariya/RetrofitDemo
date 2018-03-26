package com.jk.retrofitdemo.Sqlite_profile_example;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jk.retrofitdemo.R;
import com.jk.retrofitdemo.Sqlite_profile_example.database.DatabaseHandler;
import com.jk.retrofitdemo.Sqlite_profile_example.fragment.HomeFragment;
import com.jk.retrofitdemo.Sqlite_profile_example.fragment.TODOFragment;
import com.jk.retrofitdemo.Sqlite_profile_example.fragment.UpdateFragment;
import com.jk.retrofitdemo.Sqlite_profile_example.model.Task;
import com.jk.retrofitdemo.Sqlite_profile_example.shaaredprefs.SharedPref;

import java.util.ArrayList;

import static com.jk.retrofitdemo.Sqlite_profile_example.shaaredprefs.SharedPref.MyPREFERENCES;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTvHeader;
    private TextView mTvNavName;
    private ImageView mIvHeaderImg;
    DatabaseHandler databaseHandler;
    DatabaseHandler dbhelper;
    ArrayList<Task> list;
    String name;
    private static final String TAG = "NavigationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);

        dbhelper = new DatabaseHandler(this);

        final SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mTvHeader = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_headername);
        mIvHeaderImg = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        name = sharedpreferences.getString("name", null);
        String picture = sharedpreferences.getString("picture", null);
        mTvHeader.setText(name);
        Glide.with(this).load(picture).asBitmap().into(new BitmapImageViewTarget(mIvHeaderImg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(NavigationActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mIvHeaderImg.setImageDrawable(circularBitmapDrawable);
            }
        });

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        HomeFragment updateFragment = new HomeFragment();
        ft.replace(R.id.fl_update,updateFragment);
        ft.commit();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("taskname", name);
//        HomeFragment fragobj = new HomeFragment();
//        fragobj.setArguments(bundle);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.btn_addtask) {
            databaseHandler = new DatabaseHandler(this);
                    View v = getLayoutInflater().inflate(R.layout.dialog_todo, null);
                    final EditText mEtTask=  (EditText) v.findViewById(R.id.et_task);
                    final AlertDialog.Builder builder=new AlertDialog.Builder(NavigationActivity.this);
                    builder.setView(v).setTitle("ADD TASK").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           // String val = mEtTask.getText().toString();

                            Task task = new Task();
                            task.task=mEtTask.getText().toString();
                            task.name = name;
                            dbhelper.addtask(task);
                            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            TODOFragment updateFragment = new TODOFragment();
                            ft.replace(R.id.fl_update,updateFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                            //Toast.makeText(NavigationActivity.this,"okkkkk",Toast.LENGTH_LONG).show();

                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(NavigationActivity.this,"cancel",Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            HomeFragment updateFragment = new HomeFragment();
            ft.replace(R.id.fl_update,updateFragment);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_gallery) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            UpdateFragment updateFragment = new UpdateFragment();
            ft.replace(R.id.fl_update,updateFragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_slideshow) {
            SharedPref.logout(NavigationActivity.this);
            Intent intent=new Intent(NavigationActivity.this,MainScreenActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
