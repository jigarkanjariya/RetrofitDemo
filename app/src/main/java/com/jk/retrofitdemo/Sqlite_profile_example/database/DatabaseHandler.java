package com.jk.retrofitdemo.Sqlite_profile_example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.databinding.ObservableArrayList;
import android.util.Log;

import com.jk.retrofitdemo.Sqlite_profile_example.model.Label;
import com.jk.retrofitdemo.Sqlite_profile_example.model.Task;
import com.jk.retrofitdemo.Sqlite_profile_example.model.TaskLabelId;
import com.jk.retrofitdemo.Sqlite_profile_example.model.Taskjoinlabel;
import com.jk.retrofitdemo.Sqlite_profile_example.model.UserProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abc on 3/6/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    UserProfile userProfile = new UserProfile();
    Task task = new Task();
    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "UserProfile";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "userprofileinfo";
    private static final String TABLE_TODO = "todotask";
    private static final String TABLE_TODO_DELETE = "todotaskdelete";
    private static final String TABLE_LABEL="labelitem";
    private static final String TABLE_TASKJOINLABEL="taskjoinlabel";

    private static final String KEY_LABELNAME="labelname";
    private static final String KEY_LABELID="labelid";
    private static final String KEY_TASKID="taskid";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONENO = "phoneno";
    private static final String KEY_EMAILID = "emailid";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PICTURE = "picture";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK = "task";
    private static final String KEY_STATE = "state";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_TASKNAME = "task_name";
    private static final String TAG = "DatabaseHandler";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_USERS_TABLE = " CREATE TABLE " + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_EMAILID + " TEXT, " + KEY_PHONENO + " TEXT, " + KEY_GENDER + " TEXT, " + KEY_PICTURE + " TEXT "+")";

        String CREATE_TODO_TABLE=" CREATE TABLE " + TABLE_TODO + " ( " + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK + " TEXT, " + KEY_STATE + " TEXT, " +KEY_TASKNAME + " TEXT, " + KEY_LABELID + " TEXT," + KEY_LABELNAME + " TEXT " + " )";
        String CREATE_DELETE_TABLE = " CREATE TABLE " + TABLE_TODO_DELETE + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TASK + " TEXT "+ ")";

        String CREATE_Label_TABLE=" CREATE TABLE " + TABLE_LABEL + " ( " + KEY_LABELID + " INTEGER PRIMARY KEY," + KEY_LABELNAME + " TEXT "  + " )";

        String CREATE_TASKJOINLABEL_TABLE = " CREATE TABLE " + TABLE_TASKJOINLABEL + " ( " + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TASKID + " TEXT, " + KEY_LABELID + " TEXT " + ")";

        db.execSQL(CREATE_TODO_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_DELETE_TABLE);
        db.execSQL(CREATE_Label_TABLE);
        db.execSQL(CREATE_TASKJOINLABEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);

    }

    public void adduser(UserProfile userprofile){

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, userprofile.getName());
            values.put(KEY_PASSWORD, userprofile.getPassword());
            values.put(KEY_EMAILID, userprofile.getEmailid());
            values.put(KEY_PHONENO, userprofile.getPhoneno());
            values.put(KEY_GENDER, userprofile.getGender());
            values.put(KEY_PICTURE, userprofile.getPicture());

            db.insertOrThrow(TABLE_USER, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d(TAG, "Error while trying to add post to database");
        }
        finally {
            db.endTransaction();
        }

    }
    public String getLoginData(String name, String password) {

        String id = "";
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " where " + KEY_NAME + "='" +  name + "' AND " + KEY_PASSWORD + "='" + password+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            do {
                id = cursor.getString(cursor.getColumnIndex(KEY_ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return id;

    }
    public String getimage(String id){

        String selectQuery = "SELECT " + KEY_PICTURE + " FROM " + TABLE_USER + " where " + KEY_ID + "='" +  id +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d(TAG,"cursor is: "+cursor);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            do {
                id = cursor.getString(cursor.getColumnIndex(KEY_PICTURE));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return id;
    }
    public int updateUser(UserProfile userProfile) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, userProfile.getName());
        contentValues.put(KEY_PASSWORD, userProfile.getPassword());
        contentValues.put(KEY_EMAILID, userProfile.getEmailid());
        contentValues.put(KEY_PHONENO, userProfile.getPhoneno());
        contentValues.put(KEY_PICTURE, userProfile.getPicture());
        contentValues.put(KEY_GENDER, userProfile.getGender());

        int rows = db.update(TABLE_USER, contentValues, KEY_ID  + " = ? ",
                new String[]{String.valueOf(userProfile.id)});
        return rows;
    }

    public void addtask(Task task){

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_TASK, task.getTask());
            values.put(KEY_STATE, task.getCheck());
            values.put(KEY_TASKNAME, task.getName());
            db.insertOrThrow(TABLE_TODO, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d(TAG, "Error while trying to add post to database");
        }
        finally {
            db.endTransaction();
        }

    }

    public ArrayList<Task> getAllTask(){
        ArrayList<Task> task=new ArrayList<>();
        String USERS_SELECT_QUERY = " SELECT * FROM " + TABLE_TODO;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USERS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Task newUser = new Task();
                    newUser.id=cursor.getString(cursor.getColumnIndex(KEY_ID));
                    newUser.task=cursor.getString(cursor.getColumnIndex(KEY_TASK));
                    newUser.check= Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
                    task.add(newUser);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

        }
        return task;
    }
    public void updateTask(Task userProfile) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_STATE,userProfile.getCheck());
        db.execSQL("update " + TABLE_TODO + " set " + KEY_STATE + "='" + userProfile.getCheck() + "' where " + KEY_ID + "=" + userProfile.getId() + " AND " +  KEY_TASK + "='" + userProfile.getTask() + "'");

    }

    public void delete(Task user) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TODO,KEY_ID + " = ?",new String[]{String.valueOf(user.id)});
        db.close();
    }

    public ArrayList<Task> taskjoin(String name){
        ArrayList<Task> task=new ArrayList<>();
        String USERS_SELECT_QUERY = "SELECT " + " * " + " FROM " + TABLE_TODO + " where " + KEY_TASKNAME + "='" +  name +"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USERS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Task newUser = new Task();
                    newUser.id=cursor.getString(cursor.getColumnIndex(KEY_ID));
                    newUser.task=cursor.getString(cursor.getColumnIndex(KEY_TASK));
                    newUser.check= Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
                    task.add(newUser);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return task;
    }
    public void deletetask(String name){

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_TASK, name);
            db.insertOrThrow(TABLE_TODO_DELETE, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d(TAG, "Error while trying to add post to database");
        }
        finally {
            db.endTransaction();
        }

    }
    public ObservableArrayList<Task> getAllDeleteTask(){
        ObservableArrayList<Task> task= new ObservableArrayList<>();
        String USERS_SELECT_QUERY = " SELECT * FROM " + TABLE_TODO_DELETE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USERS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Task newUser = new Task();
                    newUser.id=cursor.getString(cursor.getColumnIndex(KEY_ID));
                    newUser.task=cursor.getString(cursor.getColumnIndex(KEY_TASK));
                    task.add(newUser);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return task;
    }


    public void updateTODO(Task user)
    {
        SQLiteDatabase db;
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_STATE,user.getCheck());
        contentValues.put(KEY_TASK,user.getTask());


        db.execSQL("update " + TABLE_TODO + " set " + KEY_STATE + "='" + user.getCheck() + "' where " + KEY_ID + "=" + user.getId() + " AND " +  KEY_TASK + "='" + user.getTask() + "'");
        db.close();
    }

    public void addLabelitem(Label label)
    {
        db=this.getWritableDatabase();
        db.beginTransaction();
        try
        {
            ContentValues values=new ContentValues();
            values.put(KEY_LABELNAME,label.getLabelname());
            db.insertOrThrow(TABLE_LABEL, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.d("Exception",""+e);

        }
        finally {
            db.endTransaction();
        }

    }
    public ArrayList<Label> getAllLabelitem()
    {
        ArrayList<Label> labels =new ArrayList<Label>();
        String TODO_SELECTED_QUERY=" SELECT * FROM " + TABLE_LABEL;
        db=getReadableDatabase();
        Cursor cursor=db.rawQuery(TODO_SELECTED_QUERY,null);
        try {
            if (cursor.moveToFirst())
            {
                do {
                    Label newTask =new Label();

                    newTask.labelId=cursor.getInt(cursor.getColumnIndex(KEY_LABELID));
                    newTask.labelname=cursor.getString(cursor.getColumnIndex(KEY_LABELNAME));
                    labels.add(newTask);

                }while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,"Error while trying to get posts from database");
        }
        return labels;
    }
    public void deleteLabelitem(Label label) {
        db=getWritableDatabase();
        db.delete(TABLE_LABEL,KEY_LABELNAME + " = ?",new String[]{String.valueOf(label.labelname)});
        db.close();
    }
    public String labelJoin(int id)
    {
        db=getReadableDatabase();
        String labels = null;
        String TODO_SELECTED_QUERY = "SELECT " + " * " + " FROM " + TABLE_LABEL + " where " + KEY_LABELID + "='" +  id +"'";

        Cursor cursor=db.rawQuery(TODO_SELECTED_QUERY,null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Label newtask =new Label();

                    newtask.labelname=cursor.getString(cursor.getColumnIndex(KEY_LABELNAME));
                    labels= newtask.getLabelname();

                }while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        }

        return labels;
    }

    public int updateLable(Task task)
    {
        db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_LABELNAME,task.getTaskLabelName());
        contentValues.put(KEY_LABELID,task.getTaskLabelid());
        contentValues.put(KEY_ID,task.getId());
        int value= db.update(TABLE_TODO,contentValues,KEY_ID +" = ?", new String[]{String.valueOf(task.id)});
        Log.d("HEY","HERE"+task.id);

        return value;

    }

    public void addmorelabel(Taskjoinlabel taskjoinlabel){

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_TASKID, taskjoinlabel.getTaskid());
            values.put(KEY_LABELID, taskjoinlabel.getLabelid());

            db.insertOrThrow(TABLE_TASKJOINLABEL, null, values);
            db.setTransactionSuccessful();
        }
        catch (Exception e){
            Log.d(TAG, "Error while trying to add post to database");
        }
        finally {
            db.endTransaction();
        }

    }
    public void addTaskLabelid(TaskLabelId taskLabelId) {
        db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LABELID, taskLabelId.getLabelid());
            values.put(KEY_TASKID, taskLabelId.getTaskid());
            db.insertOrThrow(TABLE_TASKJOINLABEL, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("Exception", "" + e);

        } finally {
            db.endTransaction();
        }

    }
    public ArrayList<Label> joinTaskLabel(int taskid) {
        db = getReadableDatabase();
        ArrayList<TaskLabelId> labels = new ArrayList<TaskLabelId>();
        ArrayList<Label> name = new ArrayList<>();
        String TODO_SELECTED_QUERY = " SELECT * FROM " + TABLE_TASKJOINLABEL + " WHERE " + KEY_TASKID + "='" + taskid + "'";
        Log.d(TAG, " " + TODO_SELECTED_QUERY);
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TODO_SELECTED_QUERY, null);
        try {
            Log.d(TAG, "joinTaskLabel: ........" + cursor.getCount());
            while (cursor.moveToNext()) {
                Label l = new Label();
                TaskLabelId taskLabelId = new TaskLabelId();
                taskLabelId.labelid = cursor.getInt(cursor.getColumnIndex(KEY_LABELID));

                l.labelname = labelJoin(taskLabelId.labelid);
                name.add(l);
                labels.add(taskLabelId);

            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            cursor.close();
        }
        Log.d(TAG, "joinTaskLabel: Name Update" + name);

        return name;
    }
    public ArrayList<Task> getAllTodo() {
        ArrayList<Label> labellist = getAllLabelitem();
       // ArrayList<TaskLabelId> taskLabelIds = getAllTaskLabelId();
//        HashMap<Integer, Label> hashMap = new HashMap<>();
//        for (Label obj : labellist) {
//            hashMap.put(obj.getLabelId(), obj);
//        }
        Map<Integer, String> hashMap1 = new HashMap<>();

        ArrayList<Task> task = new ArrayList<Task>();
        ArrayList<TaskLabelId> list = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        String TODO_SELECTED_QUERY = " SELECT * FROM " + TABLE_TODO;
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TODO_SELECTED_QUERY, null);
        try {
            while (cursor.moveToNext()) {
                Task newTask = new Task();
                newTask.id = cursor.getString(cursor.getColumnIndex(KEY_ID));
                newTask.isChecked = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(KEY_STATE)));
                newTask.task = cursor.getString(cursor.getColumnIndex(KEY_TASK));
                int item = cursor.getInt(cursor.getColumnIndex(KEY_LABELID));
                newTask.setLabels(joinTaskLabel(Integer.parseInt(newTask.id)));
                task.add(newTask);
            }

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            cursor.close();
        }

        return task;
    }



}
