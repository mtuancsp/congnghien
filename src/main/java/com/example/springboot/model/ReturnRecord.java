package com.example.springboot.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "return_records")
public class ReturnRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

    private String borrowCode;
    private LocalDateTime returnDate;

    public ReturnRecord() {

    }

    public ReturnRecord(Book book, String borrowCode, LocalDateTime returnDate) {
        this.book = book;
        this.borrowCode = borrowCode;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getBorrowCode() {
        return borrowCode;
    }

    public void setBorrowCode(String borrowCode) {
        this.borrowCode = borrowCode;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
}
