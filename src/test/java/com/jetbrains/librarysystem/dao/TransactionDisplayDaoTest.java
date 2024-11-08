package com.jetbrains.librarysystem.dao;

import com.jetbrains.librarysystem.dao.TransactionDisplayDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionDisplayDaoTest {

    private TransactionDisplayDao transactionDisplayDao;

    @BeforeEach
    public void setUp() {
        transactionDisplayDao = TransactionDisplayDao.getInstance();
    }

    @Test
    public void testSearchTransactionDisplayToReturn() {
        String searchText = "document title";

        try {
            transactionDisplayDao.searchTransactionDisplayToReturn(searchText);
        } catch (Exception e) {
            fail("Exception thrown during search: " + e.getMessage());
        }
    }

    @Test
    public void testSearchTransactionDisplayToQuerry() {
        String searchText = "user name";

        try {
            transactionDisplayDao.searchTransactionDisplayToQuerry(searchText);
        } catch (Exception e) {
            fail("Exception thrown during search: " + e.getMessage());
        }
    }

    @Test
    public void testSearchTransactionDisplayToReturn_NoResults() {
        String searchText = "nonexistent";

        try {
            transactionDisplayDao.searchTransactionDisplayToReturn(searchText);
        } catch (Exception e) {
            fail("Exception thrown during search: " + e.getMessage());
        }
    }

    @Test
    public void testSearchTransactionDisplayToQuerry_NoResults() {
        String searchText = "nonexistent";

        try {
            transactionDisplayDao.searchTransactionDisplayToQuerry(searchText);
        } catch (Exception e) {
            fail("Exception thrown during search: " + e.getMessage());
        }
    }
}
