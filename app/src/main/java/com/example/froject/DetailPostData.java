package com.example.froject;

import androidx.annotation.Nullable;

public class DetailPostData {
    String inputContentTitle;
    String inputContent;
    String inputBigCategory;
    String inputSmallCategory;
    String countPeople;

    public DetailPostData(String inputContentTitle, String inputContent) {
        this.inputContentTitle = inputContentTitle;
        this.inputContent = inputContent;
    }

    public DetailPostData(@Nullable String inputContent, @Nullable String inputBigCategory, @Nullable String inputSmallCategory, @Nullable String countPeople) {
        this.inputContent = inputContent;
        this.inputBigCategory = inputBigCategory;
        this.inputSmallCategory = inputSmallCategory;
        this.countPeople = countPeople;
    }

    public String getInputContentTitle() { return inputContentTitle; }
    public void setInputContentTitle(String inputContentTitle) { this.inputContentTitle = inputContentTitle; }

    public String getInputContent() { return inputContent; }
    public void setInputContent(String inputContent) { this.inputContent = inputContent; }

    public String getInputBigCategory() { return inputBigCategory; }
    public void setInputBigCategory(String inputBigCategory) { this.inputBigCategory = inputBigCategory; }

    public String getInputSmallCategory() { return inputSmallCategory; }
    public void setInputSmallCategory(String inputSmallCategory) { this.inputSmallCategory = inputSmallCategory; }

    public String getCountPeople() { return countPeople; }
    public void setCountPeople(String countPeople) { this.countPeople = countPeople; }

}
