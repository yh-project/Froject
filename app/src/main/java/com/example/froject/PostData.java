package com.example.froject;

import androidx.annotation.Nullable;

import java.sql.Timestamp;

public class PostData {
    String title;
    String place;
    String date;

    // 차례대로
    // 메인or분야별 내용,  대분야,  소분야,  분야별 구인 수,  총 구인 수

    String inputContent;
    String inputBigCategory;
    String inputSmallCategory;
    String countPeople;
    String totalCount;

    //Timestamp writetime;

    public PostData() { }

    public PostData(String title, String place, String date) {
        this.title = title;
        this.place = place;
        this.date = date;
    }

    // 게시글 화면에서 보일 제목과 메인내용부분 생성자
    public PostData(String title, String inputContent, String place, String date, String totalCount) {
        this.title = title;
        this.inputContent = inputContent;
        this.place = place;
        this.date = date;
        this.totalCount = totalCount;
    }

    // 게시글 화면에서 보일 분야별내용부분 생성자
    public PostData(String inputContent, String inputBigCategory, String inputSmallCategory, String countPeople) {
        this.inputContent = inputContent;
        this.inputBigCategory = inputBigCategory;
        this.inputSmallCategory = inputSmallCategory;
        this.countPeople = countPeople;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*public Timestamp getWritetime() {
        return writetime;
    }

    public void setWritetime(Timestamp writetime) {
        this.writetime = writetime;
    }*/


    // 추가한 것들 관련 getter, setter
    public String getInputContent() { return inputContent; }
    public void setInputContent(String inputContent) { this.inputContent = inputContent; }

    public String getInputBigCategory() { return inputBigCategory; }
    public void setInputBigCategory(String inputBigCategory) { this.inputBigCategory = inputBigCategory; }

    public String getInputSmallCategory() { return inputSmallCategory; }
    public void setInputSmallCategory(String inputSmallCategory) { this.inputSmallCategory = inputSmallCategory; }

    public String getCountPeople() { return countPeople; }
    public void setCountPeople(String countPeople) { this.countPeople = countPeople; }

    public String getTotalCount() { return totalCount; }
    public void setTotalCount(String totalCount) { this.totalCount = totalCount; }



}
