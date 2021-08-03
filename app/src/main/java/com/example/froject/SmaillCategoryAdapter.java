package com.example.froject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SmaillCategoryAdapter extends RecyclerView.Adapter<SmallHolder> {
    private String[] list;

    SmaillCategoryAdapter(String[] list) { this.list = list; }

    @NonNull
    @Override
    public SmallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_smallcategory, parent, false);
        return new SmallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmallHolder holder, int position) {
        holder.onBind(list[position]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

class SmallHolder extends RecyclerView.ViewHolder {
    TextView smallcategoryname;

    public SmallHolder(@NonNull View itemView) {
        super(itemView);
        smallcategoryname = itemView.findViewById(R.id.smallcategoryname);
    }

    void onBind(String name) { smallcategoryname.setText(name); }
}
