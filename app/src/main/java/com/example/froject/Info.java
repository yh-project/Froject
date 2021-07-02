package com.example.froject;

import android.widget.EditText;

public class Info {
    private String name;
    private String number;
    private String date;
    private String univ;

    public Info(String name, String number, String date, String univ) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.univ = univ;
    }

    public String getname() {
        return this.name;
    }
    public void setname(String name) {
        this.name=name;
    }


    public String getnumber() {
        return this.number;
    }
    public void setnumber(String number) {
        this.number=number;
    }


    public String getdate() {
        return this.date;
    }
    public void setdate(String date) {
        this.date=date;
    }


    public String getuniv() {
        return this.univ;
    }
    public void setuniv(String univ) {
        this.univ=univ;
    }
}
