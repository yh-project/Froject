package com.example.froject;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WritingAdapter extends RecyclerView.Adapter<WriteHolder> {
    private ArrayList<WriteData> list;
    private Context context;
    private AddClickListener addClickListener;
    private DelClickListener delClickListener;

    WritingAdapter(ArrayList<WriteData> list) { this.list = list; }

    public void additem(WriteData postData) {
        list.add(postData);
    }

    public void resetItem(ArrayList<WriteData> list) { this.list = list; }
    public ArrayList<WriteData> getItem() { return list; }

    @NonNull
    @Override
    public WriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.w("omg","1on create");
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_categorycontent, parent, false);

        return new WriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WriteHolder holder, int position) {
        Log.w("omg","2on bindviewholder");
        //holder.onBind(list.get(position));
        holder.setAddClickListener(addClickListener);
        holder.setDelClickListener(delClickListener);

        String[] bigcategory = holder.itemView.getResources().getStringArray(R.array.Bigcategory);
        ArrayList<String> a = new ArrayList<>(Arrays.asList(bigcategory));  a.remove(a.size()-1);
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

        if (!list.get(position).getBigCategory().equals("")) {
            holder.Smalladapter = new ArrayAdapter<String>(holder.itemView.getContext(),android.R.layout.simple_spinner_dropdown_item,array[a.indexOf(list.get(position).getBigCategory())]);
        }
        holder.onBind(list.get(position));
        holder.Bigspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                Log.w("omg","start bindholder");
                list.get(position).setBigCategory(holder.Bigadapter.getItem(position2));
                holder.Smalladapter = new ArrayAdapter<String>(holder.itemView.getContext(), android.R.layout.simple_spinner_dropdown_item, array[position2]);
                //Smalladapter = ArrayAdapter.createFromResource(v.getContext(), R.array.Designcategory, android.R.layout.simple_spinner_dropdown_item);
                holder.Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.Smallspinner.setAdapter(holder.Smalladapter);
                holder.Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position3, long id) {
                        list.get(position).setSmallCategory(holder.Smalladapter.getItem(position3));
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

        /*holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msgBuilder = new AlertDialog.Builder(v.getContext())
                        .setTitle("글 삭제")
                        .setMessage("해당 카테고리 글을 삭제하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
*//*                                Log.w("omggg",list.get(position).getBigCategory());
                                Log.w("omggg",list.get(position).getSmallCategory());
                                Log.w("omggg",list.get(position).getContent());
                                Log.w("omggg",list.get(position).getCountPeople());*//*
                                Log.w("omggg","123"+holder.getBindingAdapterPosition());
                                list.remove(holder.getBindingAdapterPosition());
                                notifyItemRemoved(holder.getBindingAdapterPosition());
                                holder.setIsRecyclable(false);
                                notifyDataSetChanged();

                                //notifyItemRangeChanged(position,list.size());
                                Log.w("omggg","123"+list.get(0).getBigCategory()+list.get(1).getBigCategory());
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog.Builder msgBuilder2 = new AlertDialog.Builder(v.getContext())
                        .setTitle("내용 삭제")
                        .setMessage("해당 카테고리 내용을 초기화 하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                list.remove(0);
                                WritingAdapter.this.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog msgDlg;
                *//*if (holder.countSpinner.getSelectedItemPosition()+1 != 1) {
                    msgDlg = msgBuilder.create();
                }
                else*//* {
                    msgDlg = msgBuilder.create();
                }
                msgDlg.show();
            }
        });*/

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

    void setAddClickListener(AddClickListener addClickListener) {
        this.addClickListener = addClickListener;
    }
    void setDelClickListener(DelClickListener delClickListener) {
        this.delClickListener = delClickListener;
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
    AddClickListener addClickListener;
    DelClickListener delClickListener;
    ImageButton delButton;
    int position;


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

        delButton = v.findViewById(R.id.deleteContent);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = getBindingAdapterPosition();
                if (delClickListener != null) {
                    delClickListener.onDelClick(view, position);
                }
            }
        });
        Log.w("omg","3on writeholder");
    }

    void onBind(WriteData writeData) {
        Log.w("omg","4on bind");

        ContentText.setText(writeData.getContent());

        if (writeData.getBigCategory().equals("")) {
            Bigspinner.setSelection(0);
        }
        else {
            Log.w("omg",Bigadapter.getPosition(writeData.getBigCategory())+writeData.getBigCategory());
            Bigspinner.setSelection(Bigadapter.getPosition(writeData.getBigCategory()));
        }
        if (writeData.getSmallCategory().equals(""))
            Smallspinner.setSelection(0);

        else {
            Log.w("omg",Smalladapter.getPosition(writeData.getSmallCategory())+writeData.getSmallCategory());
            Smallspinner.setSelection(Smalladapter.getPosition(writeData.getSmallCategory()));
        }
        if (writeData.getCountPeople().equals(""))
            countSpinner.setSelection(0);
        else {
            Log.w("omg",CountAdapter.getPosition(writeData.getCountPeople()+"명")+writeData.getCountPeople());
            countSpinner.setSelection(CountAdapter.getPosition(writeData.getCountPeople()+"명"));
        }

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

    void setAddClickListener(AddClickListener addClickListener) {
        this.addClickListener = addClickListener;
    }
    void setDelClickListener(DelClickListener delClickListener) {
        this.delClickListener = delClickListener;
    }


}

interface AddClickListener {
    void onAddClick(View view, int position);
}
interface DelClickListener {
    void onDelClick(View view, int position);
}



