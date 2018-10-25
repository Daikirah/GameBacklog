package com.daikirah.joshua.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddGame extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText title, platform, notes;
    String titleString, platformString, notesString, statusString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = findViewById(R.id.add_game_status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        title = findViewById(R.id.add_game_name_input_edit_text);
        platform = findViewById(R.id.add_game_platform_input_edit_text);
        notes = findViewById(R.id.add_game_notes_input_edit_text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] game;
                titleString = title.getText().toString();
                platformString = platform.getText().toString();
                notesString = notes.getText().toString();
                if (titleString.isEmpty()) {
                    Snackbar.make(view, "Fill in a Title first!", Snackbar.LENGTH_LONG).show();
                } else if (platformString.isEmpty()) {
                    Snackbar.make(view, "Fill in a Platform first!", Snackbar.LENGTH_LONG).show();
                } else {
                    game = new String[]{titleString, platformString, notesString, statusString};
                    Intent intent = new Intent(AddGame.this, MainActivity.class);
                    intent.putExtra("game", game);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        statusString = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
