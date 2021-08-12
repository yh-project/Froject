package com.example.froject;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class PostData implements Serializable {
    String title;
    String place;
    String period;
    String content;
    String totalPeople;

    String author;      //작성자
    String Email;       //Uid 대체
    String starCount ="0";
    ArrayList<String> star = new ArrayList<>();

    String postNumber;  //post id
    Date writetime;

    // 차례대로
    // 메인or분야별 내용,  대분야,  소분야,  분야별 구인 수,  총 구인 수

    ArrayList<String> BigCategory = new ArrayList<>();
    ArrayList<String> SmallCategory = new ArrayList<>();
    ArrayList<String> CategoryContent = new ArrayList<>();
    ArrayList<String> categoryPeople = new ArrayList<>();

    public PostData() { }

    // 게시글 화면에서 보일 제목과 메인내용부분 생성자
    public PostData(String title, String place, String period, String content, String totalPeople) {
        this.title = title;
        this.place = place;
        this.period = period;
        this.content = content;
        this.totalPeople = totalPeople;
    }

    // 게시글 화면에서 보일 분야별내용부분 생성자
    /*public PostData(String Content, String BigCategory, String SmallCategory, String countPeople) {
        this.Content = Content;
        this.BigCategory = BigCategory;
        this.SmallCategory = SmallCategory;
        this.countPeople = countPeople;
    }*/

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

    public String getPeriod() {
        return period;
    }
    public void setPeriod(String period) {
        this.period = period;
    }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getTotalPeople() { return totalPeople; }
    public void setTotalPeople(String totalPeople) { this.totalPeople = totalPeople; }

    // 추가한 것들 관련 getter, setter

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getStarCount() { return starCount; }
    public void setStarCount(String starCount) { this.starCount = starCount; }

    public ArrayList<String> getStar() { return star; }
    public void setStar(ArrayList<String> star) { this.star = star; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { this.Email = email; }

    public ArrayList<String> getBigCategory() { return BigCategory; }
    public void setBigCategory(ArrayList<String> bigCategory) { this.BigCategory = bigCategory; }

    public ArrayList<String> getSmallCategory() { return SmallCategory; }
    public void setSmallCategory(ArrayList<String> smallCategory) { this.SmallCategory = smallCategory; }

    public ArrayList<String> getCategoryPeople() { return categoryPeople; }
    public void setCategoryPeople(ArrayList<String> categoryPeople) { this.categoryPeople = categoryPeople; }

    public ArrayList<String> getCategoryContent() { return CategoryContent; }
    public void setCategoryContent(ArrayList<String> categoryContent) { this.CategoryContent = categoryContent; }

    //
    public String getPostNumber() { return postNumber; }
    public void setPostNumber(String postNumber) { this.postNumber = postNumber; }

    public Date getWritetime() {
        return writetime;
    }

    public void setWritetime(Date writetime) {
        this.writetime = writetime;
    }


}