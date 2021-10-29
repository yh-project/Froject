package com.example.froject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BigCategoryAdapter extends RecyclerView.Adapter<NameHolder> {
    private String[] list;
    private ClickCallbackListener callbackListener;

    BigCategoryAdapter(String[] list) {
        this.list = list;
    }

    public void setCallbackListener(ClickCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    @NonNull
    @Override
    public NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_bigcategory, parent, false);
        return new NameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameHolder holder, int position) {
        holder.onBind(list[position], callbackListener);
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

class NameHolder extends RecyclerView.ViewHolder {
    TextView bigcategoryname;
    //View sibal;
    ClickCallbackListener callbackListener;

    public NameHolder(@NonNull View itemView) {
        super(itemView);
        bigcategoryname = itemView.findViewById(R.id.bigcategoryName);/*
        sibal = itemView.findViewById(R.id.sibal);
        int gaesibal = bigcategoryname.getWidth();

        LinearLayout.LayoutParams param_sibal = new LinearLayout.LayoutParams(30, gaesibal);
        sibal.setLayoutParams(param_sibal);*/

        bigcategoryname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackListener.callBack(bigcategoryname.getText().toString());
            }
        });
    }

    void onBind(String name, ClickCallbackListener callbackListener) {
        bigcategoryname.setText(name);
        this.callbackListener = callbackListener;
    }
}
