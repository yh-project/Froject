package com.example.froject;

import java.io.Serializable;

public class PostComments implements Serializable {
    String comment;
    String postNUmber;
    String commender;
    String commended;
    int score[] = new int[4];
    public PostComments() {
        this("","","","");
    }

    public PostComments(String comment, String postNUmber, String commender, String commended) {
        this.comment = comment;
        this.postNUmber = postNUmber;
        this.commender = commender;
        this.commended = commended;
    }

    public PostComments(String comment, String postNUmber, String commender, String commended, int[] score) {
        this.comment = comment;
        this.postNUmber = postNUmber;
        this.commender = commender;
        this.commended = commended;
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int[] getScore() {
        return score;
    }

    public void setScore(int[] score) {
        this.score = score;
    }

    public String getPostNUmber() {
        return postNUmber;
    }

    public void setPostNUmber(String postNUmber) {
        this.postNUmber = postNUmber;
    }

    public String getCommender() {
        return commender;
    }

    public void setCommender(String commender) {
        this.commender = commender;
    }

    public String getCommended() {
        return commended;
    }

    public void setCommended(String commended) {
        this.commended = commended;
    }

}
