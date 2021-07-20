package com.example.muonsach.obj;

public class Category {
    private String imgCategory;
    private String nameCategory;

    public Category(String imgCategory, String nameCategory) {
        this.imgCategory = imgCategory;
        this.nameCategory = nameCategory;
    }

    public Category() {
    }

    public String getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(String imgCategory) {
        this.imgCategory = imgCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }
}
