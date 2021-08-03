package com.example.froject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {
    private ArrayList<PostData> list;

    PostAdapter(ArrayList<PostData> list) { this.list = list; }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_postlist, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
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

class PostHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView updatetime1;
    TextView updatetime2;

    public PostHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.posttitle);
        updatetime1 = itemView.findViewById(R.id.uploadtime1);
        updatetime2 = itemView.findViewById(R.id.uploadtime2);
    }

    void onBind(PostData postData) {
        title.setText(postData.getTitle());
        updatetime1.setText(postData.getPlace());
        updatetime2.setText(postData.getDate());
    }
}
