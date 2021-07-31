package com.example.froject;

public class SearchRankData {
    String rank;
    String rankContent;

    public SearchRankData(String rank, String rankContent) {
        this.rank = rank;
        this.rankContent = rankContent;
    }

    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }




    public String getRankContent() {
        return rankContent;
    }
    public void setRankContent(String rankContent) {
        this.rankContent = rankContent;
    }
}
