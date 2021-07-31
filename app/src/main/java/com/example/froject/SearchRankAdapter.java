package com.example.froject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchRankAdapter extends RecyclerView.Adapter<RankHolder> {
    private ArrayList<SearchRankData> list;

    SearchRankAdapter(ArrayList<SearchRankData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RankHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_searchrank, parent, false);
        return new RankHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

class RankHolder extends RecyclerView.ViewHolder {
    TextView rank;
    TextView rankContent;

    public RankHolder(@NonNull View itemView) {
        super(itemView);
        rank = itemView.findViewById(R.id.rank);
        rankContent = itemView.findViewById(R.id.rankContent);
    }

    void onBind(SearchRankData searchRankData) {
        rank.setText(searchRankData.getRank());
        rankContent.setText(searchRankData.getRankContent());
    }
}
