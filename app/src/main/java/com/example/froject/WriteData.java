package com.example.froject;

public class WriteData {

    String Content="";
    String BigCategory="디자인";        //스피너를 위해 기본값으로 첫번째 값을 지정해줌
    String SmallCategory="로고·브랜딩";  //이걸 안해줬더니 동기화 문제가 생김
    String countPeople="1";            //(새로운 뷰를 생성시킬 때 기존 데이터가 초기화 되는? 등의 문제가 가끔 발생함)

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
