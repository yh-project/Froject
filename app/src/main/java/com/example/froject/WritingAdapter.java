package com.example.froject;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

public class WritingAdapter extends RecyclerView.Adapter<WriteHolder> {
    private ArrayList<WriteData> list;
    private Context context;
    private AddClickListener addClickListener;
    private DelClickListener delClickListener;
    private CountChangedListener countChangedListener;
    private ViewGroup parent;
    private TextView totalCount;

    WritingAdapter(ArrayList<WriteData> list) {
        this.list = list;
    }

    public void additem(WriteData postData) {
        list.add(postData);
    }

    public void resetItem(ArrayList<WriteData> list) {
        this.list = list;
    }

    public ArrayList<WriteData> getItem() {
        return list;
    }

    @NonNull
    @Override
    public WriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.w("omg", "1on create");
        this.parent = parent;
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view;
        if (viewType == 1)
            view = inflater.inflate(R.layout.item_addcontent, parent, false);
        else
            view = inflater.inflate(R.layout.item_categorycontent, parent, false);

        WriteHolder vh = new WriteHolder(view, new WriteHolder.ITextWatcher() {
            @Override
            public void beforeTextChanged(int position, CharSequence s, int start, int count, int after) {
                // do something
            }

            @Override
            public void onTextChanged(int position, CharSequence s, int start, int before, int count) {
                list.get(position).setContent(s.toString());
            }

            @Override
            public void afterTextChanged(int position, Editable s) {
                // do something
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull WriteHolder holder, int position) {
        Log.w("omg", "2on bindviewholder");
        //holder.onBind(list.get(position));
        holder.setAddClickListener(addClickListener);
        holder.setDelClickListener(delClickListener);

        String[] bigcategory = holder.itemView.getResources().getStringArray(R.array.Bigcategory);
        ArrayList<String> a = new ArrayList<>(Arrays.asList(bigcategory));
        a.remove(a.size() - 1);
        String[] Designcategory = holder.itemView.getResources().getStringArray(R.array.Designcategory);//7 7
        String[] Developcategory = holder.itemView.getResources().getStringArray(R.array.Developcategory);//6 13
        String[] Photocategory = holder.itemView.getResources().getStringArray(R.array.Photocategory);//12 25
        String[] Translatecategory = holder.itemView.getResources().getStringArray(R.array.Translatecategory);//5 30
        String[] Plancategory = holder.itemView.getResources().getStringArray(R.array.Plancategory);//1 31
        String[] Interiorlcategory = holder.itemView.getResources().getStringArray(R.array.Interiorlcategory);//1 32

        String[][] array = new String[6][];
        array[0] = (Designcategory);
        array[1] = (Developcategory);
        array[2] = (Photocategory);
        array[3] = (Translatecategory);
        array[4] = (Plancategory);
        array[5] = (Interiorlcategory);

        holder.onBind(list.get(position));

        if (!list.get(position).getBigCategory().equals("")) {
            holder.Bigspinner.setSelection(holder.Bigadapter.getPosition(list.get(position).getBigCategory()));
            //holder.Smallspinner.setSelection(holder.Smalladapter.getPosition(list.get(position).getSmallCategory()));
            holder.countSpinner.setSelection(holder.CountAdapter.getPosition(list.get(position).getCountPeople() + "명"));
            holder.ContentText.setText(list.get(position).getContent());
        }
        else {
            holder.Bigspinner.setSelection(0);
            holder.Smallspinner.setSelection(0);
            holder.countSpinner.setSelection(0);
        }

        holder.Bigspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                Log.w("omg", "start bindholder");
                list.get(position).setBigCategory(holder.Bigadapter.getItem(position2));
                holder.Smalladapter = new ArrayAdapter<String>(holder.itemView.getContext(), R.layout.spinner_item_write, array[position2]);
                //Smalladapter = ArrayAdapter.createFromResource(v.getContext(), R.array.Designcategory, android.R.layout.simple_spinner_dropdown_item);
                holder.Smalladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.Smallspinner.setAdapter(holder.Smalladapter);
                holder.Smallspinner.setSelection(holder.Smalladapter.getPosition(list.get(position).getSmallCategory()));
                holder.Smallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position3, long id) {
                        list.get(position).setSmallCategory(holder.Smalladapter.getItem(position3));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.w("omg", "small not");
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.w("omg", "big not");
            }
        });

        if (getItemViewType(position) == 1) {
            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (addClickListener != null)
                        addClickListener.onAddClick(view, position);
                }
            });
        }

        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delClickListener != null) {
                    delClickListener.onDelClick(view, position);
                }
            }
        });

        holder.countSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                list.get(position).setCountPeople(""+(position2 + 1));
                if (countChangedListener != null)
                    countChangedListener.onCountChanged(position2+1,position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.w("omg", "count not");
            }
        });

        //list.get(position).setInputBigCategory(holder.Bigspinner.getSelectedItem().toString());
        //list.get(position).setInputSmallCategory(holder.Smallspinner.getSelectedItem().toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*@Override
    public void onViewRecycled(@NonNull @NotNull WriteHolder holder) {
        super.onViewRecycled(holder);
    }*/

    @Override
    public int getItemViewType(int position) {
        if (list.size()-1 == position && position < 2)
            return 1;
        else
            return 0;
    }

    void setAddClickListener(AddClickListener addClickListener) {
        this.addClickListener = addClickListener;
    }

    void setDelClickListener(DelClickListener delClickListener) {
        this.delClickListener = delClickListener;
    }

    public void setCountChangedListener(CountChangedListener countChangedListener) {
        this.countChangedListener = countChangedListener;
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
    ImageButton addButton;
    int position;
    ITextWatcher mTextWatcher;

    public interface ITextWatcher {
        // you can add/remove methods as you please, maybe you dont need this much
        void beforeTextChanged(int position, CharSequence s, int start, int count, int after);

        void onTextChanged(int position, CharSequence s, int start, int before, int count);

        void afterTextChanged(int position, Editable s);
    }

    public WriteHolder(@NonNull View v, ITextWatcher textWatcher) {
        super(v);
        countSpinner = v.findViewById(R.id.count);
        String[] cnt = new String[5];
        ContentText = v.findViewById(R.id.inputContent1);

        this.mTextWatcher = textWatcher;

        this.ContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mTextWatcher.beforeTextChanged(getBindingAdapterPosition(), s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextWatcher.onTextChanged(getBindingAdapterPosition(), s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                mTextWatcher.afterTextChanged(getBindingAdapterPosition(), s);
            }
        });

        for (int i = 0; i < 5; i++)
            cnt[i] = (i + 1) + "명";
        String[] bigcategory = v.getResources().getStringArray(R.array.Bigcategory);
        ArrayList<String> a = new ArrayList<>(Arrays.asList(bigcategory));
        a.remove(a.size() - 1);

        CountAdapter = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_item_write, cnt);
        CountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countSpinner.setAdapter(CountAdapter);

        Bigspinner = (Spinner) v.findViewById(R.id.bigcategory);
        Smallspinner = (Spinner) v.findViewById(R.id.smallcategory);
        Bigadapter = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_item_write, a);
        //Bigadapter = ArrayAdapter.createFromResource(v.getContext(), R.array.Bigcategory, android.R.layout.simple_spinner_dropdown_item);
        Bigadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Bigspinner.setAdapter(Bigadapter);

        delButton = v.findViewById(R.id.deleteContent);
        addButton = v.findViewById(R.id.addContents);

        /*if (this.getBindingAdapter().getItemViewType(getBindingAdapterPosition()) == 1) {
            addButton = v.findViewById(R.id.addContents);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = getBindingAdapterPosition();
                    if (addClickListener != null)
                        addClickListener.onAddClick(view, position);
                }
            });
        }*/

        Log.w("omg", "3on writeholder");
    }

    void onBind(WriteData writeData) {
        Log.w("omg", "4on bind");

        Log.w("omg", writeData.getContent());

        ContentText.setText(writeData.getContent());

        /*ContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input = s.toString();
                //Log.w("omg","before"+s);
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                *//*if (s.toString().equals("")) {
                    return;
                } else input = s.toString();*//*
                writeData.setContent(ContentText.getText().toString());
                //Log.w("omg","changed"+s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Log.w("omg","after"+s);
                //writeData.setContent(ContentText.getText().toString());
            }
        });*/

        //ContentText.setText(writeData.getContent());

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

interface CountChangedListener {
    void onCountChanged(int count, int position);
}

