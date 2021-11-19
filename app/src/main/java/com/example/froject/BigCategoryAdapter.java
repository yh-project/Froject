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
    private boolean[] is_checked;

    BigCategoryAdapter(String[] list, boolean[] is_checked) {
        this.list = list;
        this.is_checked=is_checked;
    }

    public void setCallbackListener(ClickCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    @NonNull
    @Override
    public NameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (viewType==1)
            view = inflater.inflate(R.layout.item_bigcategory, parent, false);
        else
            view = inflater.inflate(R.layout.item_bigcategory2, parent, false);
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
        if (is_checked[position])
            return 1;
        else
            return 0;
    }
}

class NameHolder extends RecyclerView.ViewHolder {
    TextView bigcategoryname;
    ClickCallbackListener callbackListener;

    public NameHolder(@NonNull View itemView) {
        super(itemView);
        bigcategoryname = itemView.findViewById(R.id.bigcategoryName);

        bigcategoryname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackListener.callBack(bigcategoryname.getText().toString(),getBindingAdapterPosition());
            }
        });
    }

    void onBind(String name, ClickCallbackListener callbackListener) {
        bigcategoryname.setText(name);
        this.callbackListener = callbackListener;
    }
}
