package com.jetbrains.librarysystem.api;

import com.jetbrains.librarysystem.data.DocumentData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class DocumentsApiServiceTest {

    private DocumentsApiService documentsApiService;

    @BeforeEach
    public void setUp() {
        documentsApiService = new DocumentsApiService();
    }

    @Test
    public void testSearchDocumentsWithQueryAndMaxResults() {
        try {
            List<DocumentData> documents = documentsApiService.searchDocuments("java programming", 5);
            Assertions.assertNotNull(documents, "The documents list should not be null.");
            Assertions.assertFalse(documents.isEmpty(), "The documents list should not be empty.");
            Assertions.assertTrue(documents.size() <= 5, "The documents list should contain at most 5 items.");

            for (DocumentData doc : documents) {
                Assertions.assertNotNull(doc.getId(), "Document ID should not be null.");
                Assertions.assertNotNull(doc.getTitle(), "Document title should not be null.");
                Assertions.assertNotNull(doc.getLinkDescription(), "Document link description should not be null.");
            }
        } catch (Exception e) {
            Assertions.fail("Exception occurred during test execution: " + e.getMessage());
        }
    }

    @Test
    public void testSearchDocumentsWithQueryOnly() {
        try {
            List<DocumentData> documents = documentsApiService.searchDocuments("data science");
            Assertions.assertNotNull(documents, "The documents list should not be null.");
            Assertions.assertFalse(documents.isEmpty(), "The documents list should not be empty.");
            Assertions.assertTrue(documents.size() <= 20, "The documents list should contain at most 20 items.");

            for (DocumentData doc : documents) {
                Assertions.assertNotNull(doc.getId(), "Document ID should not be null.");
                Assertions.assertNotNull(doc.getTitle(), "Document title should not be null.");
                Assertions.assertNotNull(doc.getLinkDescription(), "Document link description should not be null.");
            }
        } catch (Exception e) {
            Assertions.fail("Exception occurred during test execution: " + e.getMessage());
        }
    }
}
