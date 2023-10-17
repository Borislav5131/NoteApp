package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, description_input;
    Spinner status_input;
    Button update_button;

    String id, title, description, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        SetSpinnerValues();

        title_input = findViewById(R.id.title_input2);
        description_input = findViewById(R.id.description_input2);
        status_input = findViewById(R.id.status_input2);
        update_button = findViewById(R.id.update_button);

        getAndSetIntentData();

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(UpdateActivity.this);
                Note note = new Note();
                note.Id = Integer.parseInt(id);
                note.Title = title_input.getText().toString().trim();
                note.Description = description_input.getText().toString().trim();
                note.Status = status_input.getSelectedItem().toString().trim();
                db.UpdateData(note);
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("description") && getIntent().hasExtra("status")) {
            //Get
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            status = getIntent().getStringExtra("status");

            //Set
            title_input.setText(title);
            description_input.setText(description);
            status_input.setSelection(GetSpinnerSelectedValueIndex(status));
        } else {
            Toast.makeText(this, "No data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void SetSpinnerValues() {
        Spinner spinner = findViewById(R.id.status_input2);

        List<String> statusItems = new ArrayList<>();
        statusItems.add("ToDo");
        statusItems.add("Active");
        statusItems.add("Completed");
        statusItems.add("Pending");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private int GetSpinnerSelectedValueIndex(String selectedStatus) {
        List<String> statusItems = new ArrayList<>();
        statusItems.add("ToDo");
        statusItems.add("Active");
        statusItems.add("Completed");
        statusItems.add("Pending");

        int activeIndex = statusItems.indexOf(selectedStatus);
        return activeIndex;
    }
}