package com.example.froject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {
    private ArrayList<CategoryData> list;
    private ItemClickListener itemClickListener;

    CategoryAdapter(ArrayList<CategoryData> list) { this.list = list; }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_category, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(context, MainActivity.class);
                intent.putExtra("data", "board");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });


        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.setOnItemClickListener(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }
}

class CategoryHolder extends RecyclerView.ViewHolder {
    TextView categoryname;
    CircleImageView categorysrc;
    ItemClickListener itemClickListener;

    public CategoryHolder(@NonNull View itemView) {
        super(itemView);
        categoryname = itemView.findViewById(R.id.categoryname);
        categorysrc = itemView.findViewById(R.id.categorysrc);

        //this.itemClickListener = itemClickListener;
        //itemView.setOnClickListener(this);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(CategoryHolder.this, view, position);
                }
            }
        });
    }

    void onBind(CategoryData categoryData) {
        categoryname.setText(categoryData.getName());
        categorysrc.setImageResource(categoryData.getSrc());
    }

    void setOnItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

}