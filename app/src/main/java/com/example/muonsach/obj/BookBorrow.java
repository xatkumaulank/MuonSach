package com.example.muonsach.obj;

public class BookBorrow {
    private String imgBookBorrow;
    private String tvBookBorrowName;
    private String tvBookBorrowAuthorName;
    private int numStar;
    private String amountReader;
    private String amountNote;

    public BookBorrow() {
    }

    public BookBorrow(String imgBookBorrow, String tvBookBorrowName,
                      String tvBookBorrowAuthorName, int numStar, String amountReader,
                      String amountNote) {
        this.imgBookBorrow = imgBookBorrow;
        this.tvBookBorrowName = tvBookBorrowName;
        this.tvBookBorrowAuthorName = tvBookBorrowAuthorName;
        this.numStar = numStar;
        this.amountReader = amountReader;
        this.amountNote = amountNote;
    }

    public String getImgBookBorrow() {
        return imgBookBorrow;
    }

    public void setImgBookBorrow(String imgBookBorrow) {
        this.imgBookBorrow = imgBookBorrow;
    }

    public String getTvBookBorrowName() {
        return tvBookBorrowName;
    }

    public void setTvBookBorrowName(String tvBookBorrowName) {
        this.tvBookBorrowName = tvBookBorrowName;
    }

    public String getTvBookBorrowAuthorName() {
        return tvBookBorrowAuthorName;
    }

    public void setTvBookBorrowAuthorName(String tvBookBorrowAuthorName) {
        this.tvBookBorrowAuthorName = tvBookBorrowAuthorName;
    }

    public int getNumStar() {
        return numStar;
    }

    public void setNumStar(int numStar) {
        this.numStar = numStar;
    }

    public String getAmountReader() {
        return amountReader;
    }

    public void setAmountReader(String amountReader) {
        this.amountReader = amountReader;
    }

    public String getAmountNote() {
        return amountNote;
    }

    public void setAmountNote(String amountNote) {
        this.amountNote = amountNote;
    }
}
