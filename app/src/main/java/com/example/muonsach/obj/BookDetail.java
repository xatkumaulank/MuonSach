package com.example.muonsach.obj;

public class BookDetail {
    private String bookname;
    private String company;
    private String datePublic;
    private String size;
    private String bookCover;
    private int pageNum;
    private String companyPublic;
    private String content;

    public BookDetail(String bookname,String company, String datePublic, String size, String bookCover,
                      int pageNum, String companyPublic, String content) {
        this.bookname = bookname;
        this.company = company;
        this.datePublic = datePublic;
        this.size = size;
        this.bookCover = bookCover;
        this.pageNum = pageNum;
        this.companyPublic = companyPublic;
        this.content = content;
    }

    public BookDetail() {
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getDatePublic() {
        return datePublic;
    }

    public void setDatePublic(String datePublic) {
        this.datePublic = datePublic;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getCompanyPublic() {
        return companyPublic;
    }

    public void setCompanyPublic(String companyPublic) {
        this.companyPublic = companyPublic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
