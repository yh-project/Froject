package com.example.froject;

public class PostData {
    String posttitle;
    String postupdatetime1;
    String postupdatetime2;

    public PostData(String posttitle, String postupdatetime1, String postupdatetime2) {
        this.posttitle = posttitle;
        this.postupdatetime1 = postupdatetime1;
        this.postupdatetime2 = postupdatetime2;
    }

    public String getPosttitle() { return posttitle; }
    public void setPosttitle(String posttitle) { this.posttitle = posttitle; }



    public String getpostUpdatetime1() { return postupdatetime1; }
    public void setpostUpdatetime1(String postupdatetime1) { this.postupdatetime1 = postupdatetime1; }



    public String getpostUpdatetime2() { return postupdatetime2; }
    public void setpostUpdatetime2(String postupdatetime2) { this.postupdatetime2 = postupdatetime2; }

}
