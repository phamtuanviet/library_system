package com.jetbrains.librarysystem.data;

import java.sql.Date;

public class TransactionDisplay {
    private Long idTransaction;
    private Long idUser;
    private String userName;
    private String titleDocument;
    private Date borrowDate;
    private Date returnDate;

    @Override
    public String toString() {
        return "TransactionDisplay{" +
                "borrowDate=" + borrowDate +
                ", idTransaction=" + idTransaction +
                ", idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", titleDocument='" + titleDocument + '\'' +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                '}';
    }

    private String status;

    public TransactionDisplay(Long idTransaction, Long idUser, Date returnDate
            , String status, String titleDocument, String userName, Date borrowDate) {
        this.idTransaction = idTransaction;
        this.idUser = idUser;
        this.returnDate = returnDate;
        this.status = status;
        this.titleDocument = titleDocument;
        this.userName = userName;
        this.borrowDate = borrowDate;
    }

    public TransactionDisplay(Long idTransaction, Long idUser, Date borrowDate, String titleDocument, String userName) {
        this.idTransaction = idTransaction;
        this.idUser = idUser;
        this.borrowDate = borrowDate;
        this.titleDocument = titleDocument;
        this.userName = userName;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Long getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Long idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitleDocument() {
        return titleDocument;
    }

    public void setTitleDocument(String titleDocument) {
        this.titleDocument = titleDocument;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TransactionDisplay() {
    }

}
