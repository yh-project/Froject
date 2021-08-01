package com.example.froject;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MsgAdapter extends RecyclerView.Adapter<MsgHolder> {
    private ArrayList<MsgData> msglist;
    String user;

    MsgAdapter(ArrayList<MsgData> msglist, String user) {
        this.msglist = msglist;
        this.user = user;
    }

    View view;

    @Override
    public int getItemViewType(int position) {
        if (this.user == msglist.get(position).name)
            return 0;
        else
            return 1;
    }

    @NonNull
    @NotNull
    @Override
    public MsgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == 0)
            view = inflater.inflate(R.layout.item_mymsgbox, parent, false);
        else
            view = inflater.inflate(R.layout.item_othermsgobx, parent, false);

        return new MsgHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MsgHolder holder, int position) {
        holder.onBind(msglist.get(position));
    }

    @Override
    public int getItemCount() {
        return msglist.size();
    }

    /*@Override
    public int getCount() { return msglist.size();}

    @Override
    public Object getItem(int position) {
        return msglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //현재 보여줄 번째의(position)의 데이터로 뷰를 생성
        MsgData item=MsgData.get(position);

        //재활용할 뷰는 사용하지 않음!!
        View itemView=null;

        //메세지가 내 메세지인지??
        if(item.getName().equals(G.nickName)){
            itemView= layoutInflater.inflate(R.layout.my_msgbox,viewGroup,false);
        }else{
            itemView= layoutInflater.inflate(R.layout.other_msgbox,viewGroup,false);
        }

        //만들어진 itemView에 값들 설정
        CircleImageView iv= itemView.findViewById(R.id.iv);
        TextView tvName= itemView.findViewById(R.id.tv_name);
        TextView tvMsg= itemView.findViewById(R.id.tv_msg);
        TextView tvTime= itemView.findViewById(R.id.tv_time);

        tvName.setText(item.getName());
        tvMsg.setText(item.getMessage());
        tvTime.setText(item.getTime());

        Glide.with(itemView).load(item.getPofileUrl()).into(iv);

        return itemView;
    }*/


}


class MsgHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView msg;
    TextView time;
    

    public MsgHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_name);
        msg = itemView.findViewById(R.id.tv_msg);
        time = itemView.findViewById(R.id.tv_time);
    }

    void onBind(MsgData msgData) {
        name.setText(msgData.getName());
        msg.setText(msgData.getMsg());
        time.setText(msgData.getTime());
    }
}