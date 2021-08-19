package com.example.froject;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WritingAdapter extends RecyclerView.Adapter<WriteHolder> {
    private ArrayList<WriteData> list;
    private Context context;

    WritingAdapter(ArrayList<WriteData> list) { this.list = list; }

    public void additem(WriteData postData) {
        list.add(postData);
    }

    @NonNull
    @Override
    public WriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_categorycontent, parent, false);

        return new WriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WriteHolder holder, int position) {
        holder.onBind(list.get(position));

        String[] bigcategory = holder.itemView.getResources().getStringArray(R.array.Bigcategory);
        ArrayList<String> a = new ArrayList<>(Arrays.asList(bigcategory));
        a.remove(a.size()-1);
        String[] Designcategory = holder.itemView.getResources().getStringArray(R.array.Designcategory);//7 7
        String[] Developcategory = holder.itemView.getResources().getStringArray(R.array.Developcategory);//6 13
        String[] Photocategory = holder.itemView.getResources().getStringArray(R.array.Photocategory);//12 25
        String[] Translatecategory = holder.itemView.getResources().getStringArray(R.array.Translatecategory);//5 30
        String[] Plancategory = holder.itemView.getResources().getStringArray(R.array.Plancategory);//1 31
        String[] Interiorlcategory = holder.itemView.getResources().getStringArray(R.array.Interiorlcategory);//1 32

        String[][] array = new String[6][];
        array[0]=(Designcategory);
        array[1]=(Developcategory);
        array[2]=(Photocategory);
        array[3]=(Translatecategory);
        array[4]=(Plancategory);
        array[5]=(Interiorlcategory);

        holder.Bigspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                Log.w("omg","start bindholder");
                list.get(position).setBigCategory(holder.Bigadapter.getItem(position2).toString());
                holder.Smalladapter = new ArrayAdapter<String>(holder.itemView.getContext(),android.R.layout.simple_spinner_dropdown_item,array[position2]);
                //Smalladapter = ArrayAdapter.createFromResource(v.getContext(), R.array.Designcategory, android.R.layout.simple_spinner_dropdown_item);
                holder.Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.Smallspinner.setAdapter(holder.Smalladapter);
                holder.Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position3, long id) {
                        list.get(position).setSmallCategory(holder.Smalladapter.getItem(position3).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.countSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                list.get(position).setCountPeople(""+(position2+1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //list.get(position).setInputBigCategory(holder.Bigspinner.getSelectedItem().toString());
        //list.get(position).setInputSmallCategory(holder.Smallspinner.getSelectedItem().toString());
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
    final Spinner Bigspinner;
    final Spinner Smallspinner;
    final Spinner countSpinner;
    EditText ContentText;
    String input;
    ArrayAdapter<String> Bigadapter;
    ArrayAdapter<String> Smalladapter;
    ArrayAdapter<String> CountAdapter;
    public WriteHolder(@NonNull View v) {
        super(v);
        countSpinner = v.findViewById(R.id.count);
        String[] cnt = new String[5];
        ContentText = v.findViewById(R.id.inputContent1);

        for(int i=0;i<5;i++)
            cnt[i]=(i+1)+"명";
        String[] bigcategory = v.getResources().getStringArray(R.array.Bigcategory);
        ArrayList<String> a = new ArrayList<>(Arrays.asList(bigcategory));
        a.remove(a.size()-1);

        CountAdapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_dropdown_item,cnt);
        CountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countSpinner.setAdapter(CountAdapter);

        Bigspinner = (Spinner) v.findViewById(R.id.bigcategory);
        Smallspinner = (Spinner) v.findViewById(R.id.smallcategory);
        Bigadapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_dropdown_item,a);
        //Bigadapter = ArrayAdapter.createFromResource(v.getContext(), R.array.Bigcategory, android.R.layout.simple_spinner_dropdown_item);
        Bigadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Bigspinner.setAdapter(Bigadapter);
    }

    void onBind(WriteData writeData) {
        ContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input = s.toString();
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    return;
                }
                else input = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                writeData.setContent(input);
            }
        });

    }

}


