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

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {
    private ArrayList<PostData> list;
    private Context context;
    private ArrayList<DocumentReference> listDoc;
    LikeClickListener likeClickListener;
    DotClickListener dotClickListener;
    String user;

    PostAdapter(ArrayList<PostData> list) { this.list = list; }

    public void setUser(String user) { this.user = user; }
    public void setListDoc(ArrayList<DocumentReference> listDoc) { this.listDoc = listDoc; }

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
        holder.setUser(user);
        holder.onBind(list.get(position));
        holder.setLikeClickListener(likeClickListener);
        holder.setDotClickListener(dotClickListener);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(context, PostActivity.class);
                intent.putExtra("PostData",list.get(position));
                intent.putExtra("DocRef",listDoc.get(position).getId());
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

    void setLikeClickListener(LikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;
    }
    void setDotClickListener(DotClickListener dotClickListener) {
        this.dotClickListener = dotClickListener;
    }
}

class PostHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView updateTime1;
    TextView updateTime2;
    ImageView threedot;
    ImageView like;
    LikeClickListener likeClickListener;
    DotClickListener dotClickListener;
    String user;

    public PostHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.posttitle);
        updateTime1 = itemView.findViewById(R.id.uploadtime1);
        updateTime2 = itemView.findViewById(R.id.uploadtime2);
        like = itemView.findViewById(R.id.like);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getBindingAdapterPosition();
                if (likeClickListener != null) {
                    likeClickListener.onStarClick(view, position);
                }
            }
        });

        threedot = itemView.findViewById(R.id.threeDot);

        threedot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getBindingAdapterPosition();
                if(dotClickListener != null)
                    dotClickListener.onDotClick(v,position);
            }
        });
    }

    void onBind(PostData postData) {
        title.setText(postData.getTitle());
        updateTime1.setText(postData.getPlace());
        updateTime2.setText(postData.getPeriod());

        Boolean isStar = postData.getStar().contains(user);
        if (isStar) {
            like.setImageResource(R.drawable.star);
        }
        else {
            like.setImageResource(R.drawable.unstar);
        }
    }

    void setLikeClickListener(LikeClickListener likeClickListener) {
        this.likeClickListener = likeClickListener;
    }
    void setDotClickListener(DotClickListener dotClickListener) {
        this.dotClickListener = dotClickListener;
    }

    void setUser(String user) {
        this.user = user;
    }

}
interface LikeClickListener {
    void onStarClick(View view, int position);
}
interface DotClickListener {
    void onDotClick(View view, int position);
}