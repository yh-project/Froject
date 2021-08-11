package com.example.froject;

public class WriteData {

    String Content;
    String BigCategory;
    String SmallCategory;
    String countPeople;

    public WriteData() {}
    // 게시글 화면에서 보일 분야별내용부분 생성자
    public WriteData(String Content, String BigCategory, String SmallCategory, String countPeople) {
        this.Content = Content;
        this.BigCategory = BigCategory;
        this.SmallCategory = SmallCategory;
        this.countPeople = countPeople;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getBigCategory() {
        return BigCategory;
    }

    public void setBigCategory(String bigCategory) {
        BigCategory = bigCategory;
    }

    public String getCountPeople() {
        return countPeople;
    }

    public void setCountPeople(String countPeople) {
        this.countPeople = countPeople;
    }

    public String getSmallCategory() {
        return SmallCategory;
    }

    public void setSmallCategory(String smallCategory) {
        SmallCategory = smallCategory;
    }
}
