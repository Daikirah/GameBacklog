package com.daikirah.joshua.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] game;
    List<Game> gameList = new ArrayList<>();
    RecyclerView mRecyclerView;
    GameAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    public static AppDatabase db;

    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = AppDatabase.getDbInstance(this);
        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GameAdapter(gameList);
        new GameAsyncTask(TASK_GET_ALL_GAMES).execute();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, EditGame.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("EDIT_GAME", gameList.get(position));
                bundle.putInt("EDIT_GAME_POS", position);
                intent.putExtras(bundle);
                startActivityForResult(intent, 5678);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                //db.gameDao().delete(gameList.get(viewHolder.getAdapterPosition()));
                //gameList.remove(viewHolder.getAdapterPosition());
                //mRecyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                new GameAsyncTask(TASK_DELETE_GAME).execute(gameList.get(viewHolder.getAdapterPosition()));
                updateUI();
            }
        });

        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGame.class);
                startActivityForResult(intent, 1234);
            }
        });
    }

    private void updateUI() {
        mAdapter.swapList(gameList);
    }

    public void onGameDbUpdated(List list){
        gameList = list;
        updateUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234) {
            if (resultCode == Activity.RESULT_OK) {
                game = data.getStringArrayExtra("game");
                Game newGame = new Game(game[0], game[1], game[2], game[3]);
                new GameAsyncTask(TASK_INSERT_GAME).execute(newGame);
                //gameList.add(newGame);
                updateUI();
            }
        }

        if (requestCode == 5678) {
            if (resultCode == Activity.RESULT_OK) {
                Game currentGame = (Game) data.getSerializableExtra("EDIT_GAME");
                int position = data.getIntExtra("EDIT_GAME_POS", -1);
                gameList.get(position).setTitle(currentGame.getTitle());
                gameList.get(position).setPlatform(currentGame.getPlatform());
                gameList.get(position).setNotes(currentGame.getNotes());
                gameList.get(position).setStatus(currentGame.getStatus());
                gameList.get(position).setDate(Game.dateFormat.format(new Date()));
                new GameAsyncTask(TASK_UPDATE_GAME).execute(currentGame);
                updateUI();
            }
        }
    }

    public class GameAsyncTask extends AsyncTask<Game, Void, List> {

        private int taskCode;

        public GameAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(Game... games) {
            switch (taskCode){
                case TASK_DELETE_GAME:
                    db.gameDao().delete(games[0]);
                    break;
                case TASK_UPDATE_GAME:
                    db.gameDao().updateGame(games[0]);
                    break;
                case TASK_INSERT_GAME:
                    db.gameDao().insert(games[0]);
                    break;
            }
            return db.gameDao().getAll();
        }

        @Override
        public void onPostExecute(List list){
            super.onPostExecute(list);
            onGameDbUpdated(list);
        }

    }

}



