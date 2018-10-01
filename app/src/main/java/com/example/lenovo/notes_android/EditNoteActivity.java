package com.example.lenovo.notes_android;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {
    public final DatabaseHandler database = new DatabaseHandler(this);
    private String title;
    private EditText titleText;
    private EditText descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MainActivity.flag){
            setContentView(R.layout.activity_edit_note);
            //Remove or Delete the Delete Button
            Button delete = findViewById(R.id.delete_edit);
            delete.setVisibility(View.GONE);

            Button save = findViewById(R.id.save_edit);

            //Centers the Button
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) save.getLayoutParams();
            lp.horizontalBias = (float) 0.35;
            save.setLayoutParams(lp);
        }
        else {
            setContentView(R.layout.activity_edit_note);
            titleText = this.findViewById(R.id.title);
            descriptionText = this.findViewById(R.id.description);

            Intent intent = getIntent();
            title = intent.getStringExtra("Title");
            String description = intent.getStringExtra("Description");
            titleText.setText(title);
            descriptionText.setText(description);
        }

    }

    //Deletes the note
    public void delete_note(View view){
        //deletes the note with help of its Title
        if(database.delete(title, this)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(this, "Error Occured!! Try Again!!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void check_function(View view){
        if(MainActivity.flag){
            save_note(view);
        }
        else {
            update_note(view);
        }
    }

    //Update the note data
    public void update_note(View view) {
        EditText new_title = this.findViewById(R.id.title);
        String new_title_text = new_title.getText().toString();
        new_title_text = new_title_text.trim();

        if (new_title_text.isEmpty()) {
            Toast toast = Toast.makeText(this, "Title Can not be null!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            new_title.setText(new_title_text);
            EditText new_description = this.findViewById(R.id.description);
            String new_description_text = new_description.getText().toString();
            new_description_text = new_description_text.trim();
            new_description.setText(new_description_text);
            if (database.update(title, new_title_text, new_description_text, this)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(this, "Somethings Wrong!! Check The Title!!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    //inserts the note into database
    public void save_note(View view){
        EditText titleText = this.findViewById(R.id.title);
        String new_title_text = titleText.getText().toString();
        new_title_text = new_title_text.trim();
        if(new_title_text.isEmpty())
        {
            Toast toast1 = Toast.makeText(this, "Can't add null values", Toast.LENGTH_SHORT);
            toast1.show();
        }
        else {
            String title = titleText.getText().toString();
            EditText descriptionText = this.findViewById(R.id.description);
            String description = descriptionText.getText().toString();
            description = description.trim();
            descriptionText.setText(description);
            if(database.add(title, description, this)){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
