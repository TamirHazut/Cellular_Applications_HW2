package com.example.war.logic.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.war.R;
import com.example.war.logic.converter.PlayerConverter;
import com.example.war.logic.data.Location;
import com.example.war.logic.data.game.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Comparator;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private enum Type {
        HEADER(0), ITEM(1);

        private int numVal;

        Type(int numVal) { this.numVal = numVal; }

        public int getNumVal() { return numVal; }
    };
    private List<Player> players;
    private Context context;
    private FirebaseFirestore db;
    private PlayerConverter pConverter;

    public RecyclerViewAdapter(List<Player> players, Context context) {
        this.players = players;
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
        this.pConverter = new PlayerConverter();
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
            item.listitem_TXT_player_position.setText(String.format("#" + position));
            item.listitem_TXT_player_name.setText(this.players.get(position-1).getName());
            item.listitem_TXT_player_score.setText(String.valueOf(this.players.get(position-1).getScore()));
            item.location = this.players.get(position-1).getLocation();
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
        private TextView listitem_TXT_player_position;
        private TextView listitem_TXT_player_name;
        private TextView listitem_TXT_player_score;
        private Location location;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.listitem_TXT_player_position = itemView.findViewById(R.id.listitem_TXT_player_position);
            this.listitem_TXT_player_name = itemView.findViewById(R.id.listitem_TXT_player_name);
            this.listitem_TXT_player_score = itemView.findViewById(R.id.listitem_TXT_player_score);
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView headeritem_TXT_player_position;
        private TextView headeritem_TXT_player_name;
        private TextView headeritem_TXT_player_score;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.headeritem_TXT_player_position = itemView.findViewById(R.id.headeritem_TXT_player_position);
            this.headeritem_TXT_player_name = itemView.findViewById(R.id.headeritem_TXT_player_name);
            this.headeritem_TXT_player_score = itemView.findViewById(R.id.headeritem_TXT_player_score);
        }
    }
}
