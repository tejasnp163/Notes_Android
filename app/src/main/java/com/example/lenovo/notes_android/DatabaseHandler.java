package com.example.lenovo.notes_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final static String DB_NAME = "Notes";
    private final static int version = 1;

    public DatabaseHandler(@Nullable Context context) {
        super(context,DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creates the Database
        sqLiteDatabase.execSQL("CREATE TABLE NOTE_LIST(_id Integer Primary Key AutoIncrement, Title Text UNIQUE, Description Text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE NOTE_LIST");
        onCreate(sqLiteDatabase);
    }

    public boolean add(String title, String description,Context context){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", title);
        contentValues.put("Description", description);

        //if insert fails
        if(db.insert("NOTE_LIST",null, contentValues )==-1)
        {
            Toast toast = Toast.makeText(context, "Try Again!! Redundant Title!! ", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        // if successfully inserted
        else{
            Toast toast = Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
    }

    //Updates the database with the help of old_title and Catches the Exception if the tables's constraints does not matches
    public boolean update(String old_title,String new_title, String new_description, Context context)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", new_title);
        contentValues.put("Description", new_description);
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            int i = db.update("NOTE_LIST", contentValues,"Title = ?" ,new String[]{old_title});
            //if successfully updated
            if(i!=-1){
                Toast toast = Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
            //if error occurred
            else {
                Toast toast = Toast.makeText(context, "Try Again!! Redundant Title!!", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        }
        catch (SQLiteConstraintException e)
        {
            return false;
        }

    }

    public boolean delete(String title, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        //if error occurred
        if(db.delete("NOTE_LIST", "Title = ?", new String[]{title})==0){
            Toast toast = Toast.makeText(context, "Somethings Wrong!! Try Again!!", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        //if successfully deleted
        else {
            Toast toast = Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
    }
}
