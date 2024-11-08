package com.jetbrains.librarysystem.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class TransactionDaoTest {

    private TransactionDao transactionDao;

    @BeforeEach
    public void setUp() {
        transactionDao = TransactionDao.getInstance();
    }

    @Test
    public void testBorrowDocument() {
        TransactionDao transactionDaoMock = mock(TransactionDao.class);

        Long userId = 1L;
        String documentId = "doc123";
        LocalDate borrowDate = LocalDate.now();

        try {
            doNothing().when(transactionDaoMock).borrowDocument(userId, documentId, borrowDate);
            transactionDaoMock.borrowDocument(userId, documentId, borrowDate);
        } catch (Exception e) {
            fail("Exception thrown during borrowDocument: " + e.getMessage());
        }
    }

    @Test
    public void testCountBorrowedDocuments() {
        Long userId = 1L;

        try {
            Integer borrowedCount = transactionDao.countBorrowedDocuments(userId);
            assertTrue(borrowedCount >= 0, "Borrowed document count should be non-negative.");
        } catch (Exception e) {
            fail("Exception thrown during countBorrowedDocuments: " + e.getMessage());
        }
    }

    @Test
    public void testReturnDocument() {
        TransactionDao transactionDaoMock = mock(TransactionDao.class);
        Long transactionId = 1L;
        LocalDate returnDate = LocalDate.now();
        try {
            doNothing().when(transactionDaoMock).returnDocument(transactionId, returnDate);
            transactionDaoMock.returnDocument(transactionId, returnDate);
        } catch (Exception e) {
            fail("Exception thrown during returnDocument: " + e.getMessage());
        }
    }

    @Test
    public void testCountTotalTransactions() {
        try {
            Integer totalTransactions = transactionDao.countTotalTransactions();
            assertTrue(totalTransactions >= 0, "Total transactions count should be non-negative.");
        } catch (Exception e) {
            fail("Exception thrown during countTotalTransactions: " + e.getMessage());
        }
    }
}
