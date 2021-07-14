package com.example.froject;

import android.widget.EditText;

//kang

public class MakeBoard {

    private String title;        //제목
    private int people_n;       //인원수
    private String userID;      //유저아이디
    private boolean like;       //즐겨찾기
    private String location;    //위치
    private String contents;    //내용

    public MakeBoard() {}

    public MakeBoard(String title, int people_n, String userID, boolean like, String location, String contents) {
        this.title=title;
        this.people_n=people_n;
        this.userID=userID;
        this.like=like;
        this.location=location;
        this.contents=contents;
    }

    public String getTitle() {
        return title;
    }

    public int getPeople() {
        return people_n;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isLike() {
        return like;
    }

    public String getLocation() {
        return location;
    }

    public String getContents() {
        return contents;
    }

}
