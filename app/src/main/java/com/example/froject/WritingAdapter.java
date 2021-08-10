package com.example.froject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WritingAdapter extends RecyclerView.Adapter<WriteHolder> {

    private ArrayList<PostData> list;

    WritingAdapter(ArrayList<PostData> list) { this.list = list; }

    public void additem(PostData postData) {
        list.add(postData);
    }

    @NonNull
    @Override
    public WriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_categorycontent, parent, false);

        return new WriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WriteHolder holder, int position) {
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

class WriteHolder extends RecyclerView.ViewHolder {
    Spinner bigcategory;
    Spinner smallcategory;
    Spinner count;
    EditText inputContent;
    public WriteHolder(@NonNull View v) {
        super(v);
        bigcategory = v.findViewById(R.id.bigcategory);
        smallcategory = v.findViewById(R.id.smallcategory);
        count = v.findViewById(R.id.count);
        inputContent = v.findViewById(R.id.inputContent1);
    }

    void onBind(PostData postData) {
    }
}


