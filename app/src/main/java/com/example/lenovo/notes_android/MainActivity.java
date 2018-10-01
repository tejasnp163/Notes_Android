package com.example.lenovo.notes_android;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static boolean flag = false;             //flag to hide the button
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Notes> listItems;
    public final DatabaseHandler database = new DatabaseHandler(this);
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //This function will scan the database and return all the Notes
        listItems = database_scan();

        //If there is no Notes
        if(listItems.isEmpty()){
            Toast toast = Toast.makeText(this,"No Notes Available", Toast.LENGTH_LONG);
            toast.show();
        }

        //Creates New Adapter and Adds the listItems
        adapter = new NotesAdapter(listItems,this);
        //Set the created Adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
    }

    public List<Notes> database_scan(){
        listItems = new ArrayList<>();

        //Get the crated Database in Readmode
        db = database.getReadableDatabase();

        //Query returns a cursor which is set to the top of the result set
        cursor = db.query("NOTE_LIST", new String[]{"Title","Description"}, null,null,null,null,null);

        //One by one Moving cursor to next position and iterate through all the resultset
        while (cursor.moveToNext()) {
            //To get the first column
            String H = cursor.getString(0);

            //To get the second column
            String D = cursor.getString(1);
            Notes listItem =  new Notes(H, D);
            listItems.add(listItem);
        }
        return listItems;
    }

    //Release all the references when the activity will be destroyed
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
        database.close();
    }

    //add_note function will invoke another activity
    public void add_note(View view){
        flag = true;

        // Call to another Activity
        Intent intent = new Intent(this, EditNoteActivity.class);
        startActivity(intent);
    }
}
