package com.example.noteapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList<Note> notes;

    int position;

    CustomAdapter(Activity activity, Context context, ArrayList<Note> notes) {
        this.activity = activity;
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        this.position = position;

        holder.note_id_txt.setText(String.valueOf(notes.get(position).Id));
        holder.note_title_txt.setText(String.valueOf(notes.get(position).Title));
        holder.note_description_txt.setText(String.valueOf(notes.get(position).Description));
        holder.note_status_txt.setText(String.valueOf(notes.get(position).Status));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(notes.get(position).Id));
                intent.putExtra("title", String.valueOf(notes.get(position).Title));
                intent.putExtra("description", String.valueOf(notes.get(position).Description));
                intent.putExtra("status", String.valueOf(notes.get(position).Status));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView note_id_txt, note_title_txt, note_description_txt, note_status_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_id_txt = itemView.findViewById(R.id.note_id_txt);
            note_title_txt = itemView.findViewById(R.id.note_title_txt);
            note_description_txt = itemView.findViewById(R.id.note_description_txt);
            note_status_txt = itemView.findViewById(R.id.note_status_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
