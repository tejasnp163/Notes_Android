package com.example.lenovo.notes_android;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Notes> listItems;
    private Context context;

    public NotesAdapter(List<Notes> notes, Context context) {
        this.listItems = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Get the list_item, inflates it and return a View
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Notes listItem = listItems.get(i);

        final String title = listItem.getHead();
        final String description = listItem.getDesc();
        String temp;
        if(title.length()>37)
        {
            temp = title.substring(0,26) + "...";
            viewHolder.textViewTitle.setText(temp);
        }
        else {
            viewHolder.textViewTitle.setText(title);
        }

        if(description.length()>70)
        {
            temp = description.substring(0,100) + "...";
            viewHolder.textViewDescription.setText(temp);
        }
        else {
            viewHolder.textViewDescription.setText(description);
        }


        //If User click on a note it will call another activity thorough Intent
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.flag = false;
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra("Title",title);
                intent.putExtra("Description",description);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private LinearLayout parentLayout;

        //Creates a ViewHolder which can holds the Note's data
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parentlayout);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewDescription = itemView.findViewById(R.id.description);
        }
    }
}
