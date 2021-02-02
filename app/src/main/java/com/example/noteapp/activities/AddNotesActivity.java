package com.example.noteapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import com.example.noteapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;

public class AddNotesActivity extends AppCompatActivity {
    private EditText typeTextForNote;
    private FloatingActionButton saveButton;

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        init();

        noteId = getIntent().getIntExtra("noteId", -1);
        storeNote();
        saveButton.setOnClickListener(v -> {
            MainActivity.notes.set(noteId, typeTextForNote.getText().toString());
            MainActivity.adapter.notifyDataSetChanged();

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("com.example.noteapp", MODE_PRIVATE);

            HashSet<String> set = new HashSet<>(MainActivity.notes);

            sharedPref.edit().putStringSet("notes", set).apply();

            finish();
        });
    }

    private void init() {
        typeTextForNote = findViewById(R.id.typeTextForNote);
        saveButton = findViewById(R.id.saveButton);
    }

    private void storeNote(){
        if (noteId != -1){
            typeTextForNote.setText(MainActivity.notes.get(noteId));
        } else {
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
        }
    }
}