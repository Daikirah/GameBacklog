package com.daikirah.joshua.gamebacklog;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private static RecyclerViewClickListener mListener;
    private List<Game> gameList;

    public GameAdapter(List<Game> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recyclerview, parent, false);
        return new GameViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        holder.mGameTitle.setText(gameList.get(position).getTitle());
        holder.mPlatform.setText(gameList.get(position).getPlatform());
        holder.mStatus.setText(gameList.get(position).getStatus());
        holder.mLastUpdated.setText(gameList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mGameTitle;
        TextView mPlatform;
        TextView mStatus;
        TextView mLastUpdated;

        public GameViewHolder(View itemView) {
            super(itemView);
            mGameTitle = itemView.findViewById(R.id.game_name);
            mPlatform = itemView.findViewById(R.id.game_platform);
            mStatus = itemView.findViewById(R.id.game_status);
            mLastUpdated = itemView.findViewById(R.id.game_last_updated);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(RecyclerViewClickListener recyclerViewClickListener){
        GameAdapter.mListener = recyclerViewClickListener;
    }

    public void swapList (List<Game> newGames){
        gameList = newGames;
        if (newGames!= null){
            this.notifyDataSetChanged();
        }
    }
}
