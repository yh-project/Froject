package com.example.froject;

public class RecentSearchData {
    String selectedCategory;

    public RecentSearchData(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String getSelectedCategory() { return selectedCategory; }
    public void setSelectedCategory(String selectedCategory) { this.selectedCategory = selectedCategory; }
}
