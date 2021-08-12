package com.example.froject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailPostAdapter extends RecyclerView.Adapter<PostDetailHolder> {
    private ArrayList<PostData> list;

    DetailPostAdapter(ArrayList<PostData> list) { this.list = list; }

    View view;

    @Override
    public int getItemViewType(int position) {
        if(position == 0) return 0;
        else return 1;
    }

    @NonNull
    @Override
    public PostDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(viewType == 0) {
            view = inflater.inflate(R.layout.item_postdetail1, parent, false);
        } else {

            view = inflater.inflate(R.layout.item_postdetail2, parent, false);
        }
        return new PostDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostDetailHolder holder, int position) {
        if(position == 0) {
            holder.onBind1(list.get(position));
        } else {
            holder.onBind2(list.get(position),position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class PostDetailHolder extends RecyclerView.ViewHolder {
    TextView inputContentTitle;
    TextView inputContent;
    TextView inputBigCategory;
    TextView inputSmallCategory;
    TextView countPeople;
    TextView place;
    TextView period;
    TextView totalCount;

    public PostDetailHolder(@NonNull View itemView) {
        super(itemView);
        inputContentTitle = itemView.findViewById(R.id.inputContentTitle);
        inputContent = itemView.findViewById(R.id.inputContent);
        inputBigCategory = itemView.findViewById(R.id.inputBigCategory);
        inputSmallCategory = itemView.findViewById(R.id.inputSmallCategory);
        countPeople = itemView.findViewById(R.id.countPeople);
        place = itemView.findViewById(R.id.place);
        period = itemView.findViewById(R.id.period);
        totalCount = itemView.findViewById(R.id.totalCount);
    }

    void onBind1(PostData postData) {
        inputContent.setText(postData.getContent());
        place.setText(postData.getPlace());
        period.setText(postData.getPeriod());
        totalCount.setText(postData.getTotalPeople()+"명");
    }

    void onBind2(PostData postData, int position) {
        inputContent.setText(postData.getCategoryContent().get(position-1));
        inputBigCategory.setText(postData.getBigCategory().get(position-1));
        inputSmallCategory.setText(postData.getSmallCategory().get(position-1));
        countPeople.setText("구인 수 : "+postData.getCategoryPeople().get(position-1));
    }
}