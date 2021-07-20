package com.example.muonsach.obj;

public class Book {
    private String bookauthor;
    private String bookimg;
    private String bookname;
    private int rating;

    public Book() {
    }

    public Book(String bookauthor, String bookimg, String bookname, int rating) {
        this.bookauthor = bookauthor;
        this.bookimg = bookimg;
        this.bookname = bookname;
        this.rating = rating;
    }

    public String getBookauthor() {
        return bookauthor;
    }

    public void setBookauthor(String bookauthor) {
        this.bookauthor = bookauthor;
    }

    public String getBookimg() {
        return bookimg;
    }

    public void setBookimg(String bookimg) {
        this.bookimg = bookimg;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
