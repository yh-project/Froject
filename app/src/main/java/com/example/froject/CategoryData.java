package com.example.froject;

import android.graphics.drawable.Drawable;

public class CategoryData {
    String name;
    int src;

    public CategoryData(int src, String name) {
        this.src = src;
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSrc() { return src; }
    public void setSrc(int src) { this.src = src; }
}
