package com.example.muonsach.data;

import com.example.muonsach.login.User;
import com.example.muonsach.obj.Book;
import com.example.muonsach.obj.BookBorrow;
import com.example.muonsach.obj.BookDetail;
import com.example.muonsach.obj.BookShare;
import com.example.muonsach.obj.Category;
import com.example.muonsach.obj.GATMoney;
import com.example.muonsach.obj.RisingUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadData {
    public static void writeNewUser(String email, String password, String address, String fullname) {
        DatabaseReference mDatabase;
        User user = new User(email, password,address,fullname);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(User.formatEmail(email)).setValue(user);
    }
    public static void addBookToDb(String bookimg, String bookauthor, String bookname, int numstars){
        DatabaseReference databaseReference;
        Book book = new Book(bookauthor,bookimg,bookname,numstars);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("book").child(bookname).setValue(book);
    }
    public static void addBookToCategory(String category, String bookimg, String bookauthor, String bookname, int numstars){
        DatabaseReference databaseReference;
        Book book = new Book(bookauthor,bookimg,bookname,numstars);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Category").child(category).child("books").child(bookname).setValue(book);
    }
    public static void addCategoryToDb(String imgCategory, String nameCategory){
        DatabaseReference databaseReference;
        Category category = new Category(imgCategory,nameCategory);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Category").child(nameCategory).setValue(category);
    }
    public static void addRisingUserToDb(String imgRisingUser,String nameRisingUser,
            String emailRisingUser){
        DatabaseReference databaseReference;
        RisingUser risingUser = new RisingUser(imgRisingUser,nameRisingUser,emailRisingUser);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("RisingUser").child(emailRisingUser).setValue(risingUser);
    }

    public static void addBookBorrowtoDb(String imgBookBorrow, String tvBookBorrowName,
                                         String tvBookBorrowAuthorName, int numStar, String amountReader,
                                         String amountNote){
        DatabaseReference databaseReference;
        BookBorrow bookBorrow = new BookBorrow(imgBookBorrow,tvBookBorrowName,
                tvBookBorrowAuthorName,numStar,amountReader,
                amountNote);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("bookBorrow").child(tvBookBorrowName).setValue(bookBorrow);
    }

    public static void addBookSharedToDb(String imgBookShare, int numStars, String tvBookShareName){
        DatabaseReference databaseReference;
        BookShare bookShare = new BookShare(imgBookShare,numStars,tvBookShareName);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("bookShare").child(tvBookShareName).setValue(bookShare);
    }
    public static void addBookDetailToDb(String bookname, String company, String datePublic, String size, String bookCover,
                                         int pageNum, String companyPublic, String content){
        DatabaseReference databaseReference;
        BookDetail bookDetail = new BookDetail(bookname,company,datePublic,size,bookCover,pageNum,companyPublic,content);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("bookDetail").child(bookname).setValue(bookDetail);
    }
    public static void addGATMoneyToDb(String serialNumber,  int value){
        DatabaseReference databaseReference;
        GATMoney gatMoney = new GATMoney(serialNumber,value);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("GATMoney").child(serialNumber).setValue(gatMoney);
    }

}
