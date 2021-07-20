package com.example.muonsach.obj;

public class ItemCart {
    private String imgBookCart;
    private String nameBookCart;
    private String priceBookCart;
    private String imgUserBorrowCart;
    private String nameUserBorrowCart;
    private String addressUserBorrowCart;
    private String emailUserBorrowCart;
    public ItemCart(String imgBookCart, String nameBookCart, String priceBookCart,
                    String imgUserBorrowCart, String nameUserBorrowCart, String addressUserBorrowCart,
                    String emailUserBorrowCart) {
        this.imgBookCart = imgBookCart;
        this.nameBookCart = nameBookCart;
        this.priceBookCart = priceBookCart;
        this.imgUserBorrowCart = imgUserBorrowCart;
        this.nameUserBorrowCart = nameUserBorrowCart;
        this.addressUserBorrowCart = addressUserBorrowCart;
        this.emailUserBorrowCart = emailUserBorrowCart;
    }

    public ItemCart() {
    }

    public String getEmailUserBorrowCart() {
        return emailUserBorrowCart;
    }

    public void setEmailUserBorrowCart(String emailUserBorrowCart) {
        this.emailUserBorrowCart = emailUserBorrowCart;
    }

    public String getImgBookCart() {
        return imgBookCart;
    }

    public void setImgBookCart(String imgBookCart) {
        this.imgBookCart = imgBookCart;
    }

    public String getNameBookCart() {
        return nameBookCart;
    }

    public void setNameBookCart(String nameBookCart) {
        this.nameBookCart = nameBookCart;
    }

    public String getPriceBookCart() {
        return priceBookCart;
    }

    public void setPriceBookCart(String priceBookCart) {
        this.priceBookCart = priceBookCart;
    }

    public String getImgUserBorrowCart() {
        return imgUserBorrowCart;
    }

    public void setImgUserBorrowCart(String imgUserBorrowCart) {
        this.imgUserBorrowCart = imgUserBorrowCart;
    }

    public String getNameUserBorrowCart() {
        return nameUserBorrowCart;
    }

    public void setNameUserBorrowCart(String nameUserBorrowCart) {
        this.nameUserBorrowCart = nameUserBorrowCart;
    }

    public String getAddressUserBorrowCart() {
        return addressUserBorrowCart;
    }

    public void setAddressUserBorrowCart(String addressUserBorrowCart) {
        this.addressUserBorrowCart = addressUserBorrowCart;
    }
}
