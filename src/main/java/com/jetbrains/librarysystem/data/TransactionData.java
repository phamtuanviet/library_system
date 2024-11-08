package com.jetbrains.librarysystem.data;

import java.sql.Date;

public class TransactionData {
    private Long idTransaction;
    private Long idUser;
    private String idDocument;
    private Date borrowDate;
    private Date returnDate;
    private String status;

    public TransactionData(Date borrowDate, String idDocument, Long idTransaction, Long idUser, Date returnDate, String status) {
        this.borrowDate = borrowDate;
        this.idDocument = idDocument;
        this.idTransaction = idTransaction;
        this.idUser = idUser;
        this.returnDate = returnDate;
        this.status = status;
    }


    public TransactionData() {
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
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

}
