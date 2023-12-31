package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    EditText title_input, description_input;
    Spinner status_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        SetSpinnerValues();

        title_input = findViewById(R.id.title_input);
        description_input = findViewById(R.id.description_input);
        status_input = findViewById(R.id.status_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(v -> {
            try {
                if(!Validation.ValidateTitle(title_input.getText().toString().trim())){
                    throw new Exception("Invalid title!");
                }

                if(!Validation.ValidateDescription(description_input.getText().toString().trim())){
                    throw new Exception("Invalid description!");
                }

                if(!Validation.ValidateStatus(status_input.getSelectedItem().toString().trim())){
                    throw new Exception("Invalid status!");
                }

                DatabaseHelper db = new DatabaseHelper(AddActivity.this);
                db.AddNote(title_input.getText().toString().trim(), description_input.getText().toString().trim(), status_input.getSelectedItem().toString().trim());

                //Refresh recycle view
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Exception:" + ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void SetSpinnerValues() {
        Spinner spinner = findViewById(R.id.status_input);

        List<String> statusItems = new ArrayList<>();
        statusItems.add("ToDo");
        statusItems.add("Active");
        statusItems.add("Completed");
        statusItems.add("Pending");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}