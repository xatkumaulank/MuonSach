package com.example.muonsach.user;

public class BookCase {
    private String imgBookcaseBook;
    private String tvBookcaseBookname;
    private String tvBookcaseAuthorname;
    private String tvBookcasePrice;

    public BookCase(String imgBookcaseBook, String tvBookcaseBookname,
                    String tvBookcaseAuthorname, String tvBookcasePrice) {
        this.imgBookcaseBook = imgBookcaseBook;
        this.tvBookcaseBookname = tvBookcaseBookname;
        this.tvBookcaseAuthorname = tvBookcaseAuthorname;
        this.tvBookcasePrice = tvBookcasePrice;
    }

    public BookCase() {
    }

    public String getImgBookcaseBook() {
        return imgBookcaseBook;
    }

    public void setImgBookcaseBook(String imgBookcaseBook) {
        this.imgBookcaseBook = imgBookcaseBook;
    }

    public String getTvBookcaseBookname() {
        return tvBookcaseBookname;
    }

    public void setTvBookcaseBookname(String tvBookcaseBookname) {
        this.tvBookcaseBookname = tvBookcaseBookname;
    }

    public String getTvBookcaseAuthorname() {
        return tvBookcaseAuthorname;
    }

    public void setTvBookcaseAuthorname(String tvBookcaseAuthorname) {
        this.tvBookcaseAuthorname = tvBookcaseAuthorname;
    }

    public String getTvBookcasePrice() {
        return tvBookcasePrice;
    }

    public void setTvBookcasePrice(String tvBookcasePrice) {
        this.tvBookcasePrice = tvBookcasePrice;
    }
}
