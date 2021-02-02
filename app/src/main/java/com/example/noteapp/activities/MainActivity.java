package com.example.noteapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.noteapp.R;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView addNotes;
    private ListView notesList;

    static List<String> notes = new ArrayList<>();
    static ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("com.example.noteapp", MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPref.getStringSet("notes", null);

        if (set == null){
            notes.add("Note One");
            notes.add("Note Two");
            notes.add("Note Three");
        } else {
            notes = new ArrayList<>(set);
        }


        addNotes.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddNotesActivity.class)));
        addNote();
    }


    private void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addNotes = findViewById(R.id.addNotes);
        notesList = findViewById(R.id.notesList);
    }

    private void addNote(){
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        notesList.setAdapter(adapter);

        notesList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), AddNotesActivity.class);
            intent.putExtra("noteId", position);
            startActivity(intent);
        });

        notesList.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Remove Item")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        notes.remove(position);
                        adapter.notifyDataSetChanged();


                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("com.example.noteapp", MODE_PRIVATE);
                        HashSet<String> set = new HashSet<>(MainActivity.notes);

                        sharedPref.edit().putStringSet("notes", set).apply();
                    })
                    .setNegativeButton("No", null)
                    .create()
                    .show();

            return true;
        });
    }
}