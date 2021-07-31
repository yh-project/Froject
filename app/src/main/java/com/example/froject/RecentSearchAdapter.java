package com.example.froject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentHolder> {
    private ArrayList<RecentSearchData> list;

    RecentSearchAdapter(ArrayList<RecentSearchData> list) { this.list = list; }

    @NonNull
    @Override
    public RecentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_recentsearch, parent, false);
        return new RecentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentHolder holder, int position) {
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

class RecentHolder extends RecyclerView.ViewHolder {
    TextView selectedCategory;

    public RecentHolder(@NonNull View itemView) {
        super(itemView);
        selectedCategory = itemView.findViewById(R.id.selectedCategory);
    }

    void onBind(RecentSearchData recentSearchData) {
        selectedCategory.setText(recentSearchData.getSelectedCategory());
    }
}
