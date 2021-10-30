package com.example.froject;

import android.app.Application;

import java.io.Serializable;

public class Info implements Serializable {
    private String name;
    private String first_name;
    private String last_name;
    private String number;
    private String univ;
    private String level;
    private String major;
    private String Email;

    public Info(String first_name, String last_name, String number, String univ, String level, String major, String Email) {
        this.name = first_name + last_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.number = number;
        this.univ = univ;
        this.level = level;
        this.major = major;
        this.Email = Email;
    }

    public Info(String name, String first_name, String last_name, String number, String univ, String level, String major, String Email) {
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.number = number;
        this.univ = univ;
        this.level = level;
        this.major = major;
        this.Email = Email;
    }

    public Info() {

    }

    public Info(String Email) {
        this("","","","","","",Email);
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

    public String getuniv() {
        return this.univ;
    }
    public void setuniv(String univ) {
        this.univ=univ;
    }


    public String getlevel() {
        return this.level;
    }
    public void setlevel(String level) {
        this.level=level;
    }


    public String getmajor() {
        return this.major;
    }
    public void setmajor(String major) {
        this.major=major;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean checkNull() {
        if (this.name.length() > 0 && this.number.length() > 9 && this.univ.length() > 0 && this.level.length() > 0 && this.major.length() > 0)
            return false;
        else
            return true;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String fisrt_name) {
        this.first_name = fisrt_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
