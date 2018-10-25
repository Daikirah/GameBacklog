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

public class EditGame extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextInputEditText title, platform, notes;
    String titleString, platformString, notesString, statusString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Game game = (Game) getIntent().getSerializableExtra("EDIT_GAME");
        final int position = getIntent().getIntExtra("EDIT_GAME_POS", -1);


        Spinner spinner = findViewById(R.id.edit_game_status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(game.getStatus()));
        spinner.setOnItemSelectedListener(this);

        title = findViewById(R.id.edit_game_name_input_edit_text);
        platform = findViewById(R.id.edit_game_platform_input_edit_text);
        notes = findViewById(R.id.edit_game_notes_input_edit_text);

        title.setText(game.getTitle());
        platform.setText(game.getPlatform());
        notes.setText(game.getNotes());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleString = title.getText().toString();
                platformString = platform.getText().toString();
                notesString = notes.getText().toString();
                if (titleString.isEmpty()) {
                    Snackbar.make(view, "Fill in a Title first!", Snackbar.LENGTH_LONG).show();
                } else if (platformString.isEmpty()) {
                    Snackbar.make(view, "Fill in a Platform first!", Snackbar.LENGTH_LONG).show();
                } else {
                    game.setTitle(titleString);
                    game.setPlatform(platformString);
                    game.setNotes(notesString);
                    game.setStatus(statusString);

                    Intent intent = new Intent(EditGame.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("EDIT_GAME", game);
                    bundle.putInt("EDIT_GAME_POS", position);
                    intent.putExtras(bundle);
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
