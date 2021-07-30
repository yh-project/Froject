package com.example.froject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LikelistAdapter extends RecyclerView.Adapter<Holder> {
    private ArrayList<LikePostData> list;

    LikelistAdapter(ArrayList<LikePostData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_likelist, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class Holder extends RecyclerView.ViewHolder {
    TextView title;
    TextView updatetime1;
    TextView updatetime2;

    public Holder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.posttitle);
        updatetime1 = itemView.findViewById(R.id.uploadtime1);
        updatetime2 = itemView.findViewById(R.id.uploadtime2);
    }

    void onBind(LikePostData likePostData) {
        title.setText(likePostData.getTitle());
        updatetime1.setText(likePostData.getUpdatetime1());
        updatetime2.setText(likePostData.getUpdatetime2());
    }
}
