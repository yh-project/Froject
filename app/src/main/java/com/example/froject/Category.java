package com.example.froject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Category {
    ArrayList<String> develop = new ArrayList<String>(Arrays.asList("android", "ios", "web", "database"));
    ArrayList<String> design = new ArrayList<String>(Arrays.asList("UI", "UX"));
    ArrayList<String> manage = new ArrayList<String>(Arrays.asList("plan"));
    ArrayList<ArrayList<String>> category_list = new ArrayList<ArrayList<String>> (Arrays.asList(develop, design, manage));

    public ArrayList<String> getDevelop() {
        return develop;
    }
    public void setDevelop(ArrayList<String> develop) {
        this.develop = develop;
    }

    public ArrayList<String> getDesign() {
        return design;
    }
    public void setDesign(ArrayList<String> design) {
        this.design = design;
    }

    public ArrayList<String> getManage() {
        return manage;
    }
    public void setManage(ArrayList<String> manage) {
        this.manage = manage;
    }

    public ArrayList<ArrayList<String>> getCategory_list() {
        return category_list;
    }
    public void setCategory_list(ArrayList<ArrayList<String>> category_list) {
        this.category_list = category_list;
    }




}
