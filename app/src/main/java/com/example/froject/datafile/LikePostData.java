package com.example.froject.datafile;

public class LikePostData {
    String title = "배고파";
    String updatetime1 = "배고프다고";
    String updatetime2 = "배고프다니까?";

    public LikePostData(String title, String updatetime1, String updatetime2) {
        this.title = title;
        this.updatetime1 = updatetime1;
        this.updatetime2 = updatetime2;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }



    public String getUpdatetime1() {
        return updatetime1;
    }
    public void setUpdatetime1(String updatetime1) {
        this.updatetime1 = updatetime1;
    }


    public String getUpdatetime2() {
        return updatetime2;
    }
    public void setUpdatetime2(String updatetime2) {
        this.updatetime2 = updatetime2;
    }
}
