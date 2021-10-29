package com.example.froject;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

public class SmaillCategoryAdapter extends RecyclerView.Adapter<SmallHolder> {
    private String[] list;
    private ClickCallbackListener2 callbackListener2;
    private int selected=0;
    private boolean[] is_checked;

    SmaillCategoryAdapter(String[] list, boolean[] is_checked) { this.list = list; this.is_checked=is_checked; }

    public void setCallbackListener2(ClickCallbackListener2 callbackListener2) {
        this.callbackListener2 = callbackListener2;
    }

    @NonNull
    @Override
    public SmallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if(viewType==1)
            view = inflater.inflate(R.layout.item_smallcategory2, parent, false);
        else
            view = inflater.inflate(R.layout.item_smallcategory, parent, false);

        return new SmallHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmallHolder holder, int position) {
        holder.onBind(list[position],callbackListener2);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    @Override
    public int getItemViewType(int position) {
        if (is_checked[position]) return 1;
        return 0;
    }
}

class SmallHolder extends RecyclerView.ViewHolder {
    TextView smallcategoryname, prevcategory;
    ClickCallbackListener callbackListener;
    ClickCallbackListener2 callbackListener2;
    ConstraintLayout prevlayout = null, smalllayout;
    View prev = null;

    public SmallHolder(@NonNull View itemView) {
        super(itemView);
        smallcategoryname = itemView.findViewById(R.id.smallcategoryname);
        smalllayout = itemView.findViewById(R.id.smallItem);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackListener2.callBack(smallcategoryname.getText().toString(),getBindingAdapterPosition());
            }
        });
    }

    void onBind(String name, ClickCallbackListener2 callbackListener2) {
        smallcategoryname.setText(name);
        this.callbackListener2 = callbackListener2;
    }

    View get_prev() {
        return prev;
    }
}
