package com.jk.retrofitdemo.Sqlite_profile_example.shaaredprefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abc on 3/7/2018.
 */

public class SharedPref  {

    public static final String MyPREFERENCES = "MyPrefs";
    public static void saveSrtingData(Context mContext, String key, String value) {
        SharedPreferences sharedpreferences;
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringData(Context mContext, String key) {
        String value = "";
        SharedPreferences sharedpreferences;
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        value = sharedpreferences.getString(key,"");
        return value;
    }

    public static void logout(Context mcontext){
        SharedPreferences preferences=mcontext.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.commit();
    }
}
