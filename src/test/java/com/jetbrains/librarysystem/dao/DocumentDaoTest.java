package com.jetbrains.librarysystem.dao;

import com.jetbrains.librarysystem.data.DocumentData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentDaoTest {
    private DocumentDao documentDao;
    private String testDocumentId = "doc123";
    private DocumentData testDocument;

    @BeforeEach
    public void setUp() {
        documentDao = DocumentDao.getInstance();
        testDocument = new DocumentData(
                testDocumentId,
                "Test Title",
                "Test Author",
                "Test Category",
                5,
                "Test Image",
                "Test Link Description"
        );
        documentDao.addDocument(
                testDocument.getId(),
                testDocument.getTitle(),
                testDocument.getAuthor(),
                testDocument.getCategory(),
                testDocument.getQuantity(),
                testDocument.getImage(),
                testDocument.getLinkDescription()
        );
    }

    @AfterEach
    public void tearDown() {
        documentDao.deleteDocument(testDocumentId);
    }

    @Test
    public void testCheckExsitingById() {
        assertTrue(documentDao.checkExsitingById(testDocumentId), "Document should exist in the database.");
        assertFalse(documentDao.checkExsitingById("nonexistent_id"), "Document should not exist in the database.");
    }

    @Test
    public void testAddAndDeleteDocument() {
        documentDao.addDocument("doc124", "Another Title", "Another Author", "Another Category", 3, "Another Image", "Another Link");
        assertTrue(documentDao.checkExsitingById("doc124"), "New document should be added successfully.");
        documentDao.deleteDocument("doc124");
        assertFalse(documentDao.checkExsitingById("doc124"), "Document should be deleted successfully.");
    }

    @Test
    public void testUpdateDocument() {
        documentDao.updateDocument(testDocumentId, 10);
        Integer quantity = documentDao.quantityDocument(testDocumentId);
        assertNotNull(quantity, "Document quantity should not be null.");
        assertEquals(10, quantity, "Document quantity should match the updated value.");
    }

    @Test
    public void testCountTotalDocuments() {
        Integer count = documentDao.countTotalDocuments();
        assertNotNull(count, "Document count should not be null.");
        assertTrue(count > 0, "Document count should be greater than 0.");
    }
}
