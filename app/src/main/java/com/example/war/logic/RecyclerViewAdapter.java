package com.example.war.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.war.R;
import com.example.war.logic.data.entity.Player;
import com.example.war.logic.view.OnClickInterface;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private enum Type {
        HEADER(0), ITEM(1);

        private final int numVal;

        Type(int numVal) { this.numVal = numVal; }

        public int getNumVal() { return numVal; }
    }
    private final List<Player> players;
    private final OnClickInterface onClickInterface;

    public RecyclerViewAdapter(List<Player> players, Context context, OnClickInterface onClickInterface) {
        this.players = players;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Type.HEADER.getNumVal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_layout_headeritem, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == Type.ITEM.getNumVal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_layout_listitem, parent, false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder header = (HeaderViewHolder) holder;
            header.headeritem_TXT_player_position.setText(R.string.headeritem_player_rank);
            header.headeritem_TXT_player_name.setText(R.string.headeritem_player_name);
            header.headeritem_TXT_player_score.setText(R.string.headeritem_player_score);
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder item = (ItemViewHolder) holder;
            item.listitem_TXT_player_position.setText("#" + position);
            item.listitem_TXT_player_name.setText(this.players.get(position-1).getName());
            item.listitem_TXT_player_score.setText(String.valueOf(this.players.get(position-1).getScore()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 ? Type.HEADER.getNumVal() : Type.ITEM.getNumVal());
    }

    @Override
    public int getItemCount() {
        return this.players.size()+1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView listitem_TXT_player_position;
        private final TextView listitem_TXT_player_name;
        private final TextView listitem_TXT_player_score;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.listitem_TXT_player_position = itemView.findViewById(R.id.listitem_TXT_player_position);
            this.listitem_TXT_player_name = itemView.findViewById(R.id.listitem_TXT_player_name);
            this.listitem_TXT_player_score = itemView.findViewById(R.id.listitem_TXT_player_score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClickInterface.setOnClick(players.get(position-1).getLocation());
                }
            });
        }

    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView headeritem_TXT_player_position;
        private final TextView headeritem_TXT_player_name;
        private final TextView headeritem_TXT_player_score;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.headeritem_TXT_player_position = itemView.findViewById(R.id.headeritem_TXT_player_position);
            this.headeritem_TXT_player_name = itemView.findViewById(R.id.headeritem_TXT_player_name);
            this.headeritem_TXT_player_score = itemView.findViewById(R.id.headeritem_TXT_player_score);
        }
    }
}
