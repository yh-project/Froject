package com.example.froject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//작성한 후기 보기
public class FinishAdapter extends RecyclerView.Adapter<FinishHolder> {
    private ArrayList<PostData> list;
    private Context context;
    private ArrayList<DocumentReference> listDoc;

    FinishAdapter(ArrayList<PostData> list) { this.list = list; }

    @Override
    public FinishHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_finishproject, parent, false);

        return new FinishHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FinishHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PostActivity.class);
                intent.putExtra("postData",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
class FinishHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView timeStamp;
    ImageView threedot;
    String user;


    public FinishHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.posttitle);
        timeStamp = itemView.findViewById(R.id.timestamp);
        threedot = itemView.findViewById(R.id.threeDot);
    }
}
