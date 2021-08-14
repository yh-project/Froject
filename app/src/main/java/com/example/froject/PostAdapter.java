package com.example.froject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {
    private ArrayList<PostData> list;
    private Context context;

    PostAdapter(ArrayList<PostData> list) { this.list = list; }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_postlist, parent, false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.onBind(list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(context, PostActivity.class);
                intent.putExtra("PostData",list.get(position));
                context.startActivity(intent);
            }
        });
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
    ImageView like;

    public PostHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.posttitle);
        updatetime1 = itemView.findViewById(R.id.uploadtime1);
        updatetime2 = itemView.findViewById(R.id.uploadtime2);
        like = itemView.findViewById(R.id.like);
    }

    void onBind(PostData postData) {
        title.setText(postData.getTitle());
        updatetime1.setText(postData.getPlace());
        updatetime2.setText(postData.getPeriod());
    }
}
