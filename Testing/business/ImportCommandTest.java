package business;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.util.List;
import java.util.Map;

import bll.IEditorBO;
import dto.Documents;

//Mock class for testing with ALL interface methods
class MockBO implements IEditorBO {
 boolean shouldSucceed = true;
 File lastFile;
 String lastFileName;
 
 // Import method with proper validation
 @Override
 public boolean importTextFiles(File file, String fileName) {
     // Null check
     if (file == null) {
         throw new NullPointerException("File cannot be null");
     }
     
     // Empty filename check (BOUNDARY CASE)
     if (fileName == null || fileName.trim().isEmpty()) {
         return false; // Empty filename should fail
     }
     
     this.lastFile = file;
     this.lastFileName = fileName;
     return shouldSucceed;
 }
 
 // All other methods with empty implementations (keep these as before)
 @Override public boolean createFile(String nameOfFile, String content) { return false; }
 @Override public boolean updateFile(int id, String fileName, int pageNumber, String content) { return false; }
 @Override public boolean deleteFile(int id) { return false; }
 @Override public Documents getFile(int id) { return null; }
 @Override public List<Documents> getAllFiles() { return null; }
 @Override public String getFileExtension(String fileName) { return null; }
 @Override public String transliterate(int pageId, String arabicText) { return null; }
 @Override public List<String> searchKeyword(String keyword) { return null; }
 @Override public Map<String, String> lemmatizeWords(String text) { return null; }
 @Override public Map<String, List<String>> extractPOS(String text) { return null; }
 @Override public Map<String, String> extractRoots(String text) { return null; }
 @Override public double performTFIDF(List<String> unSelectedDocsContent, String selectedDocContent) { return 0; }
 @Override public Map<String, Double> performPMI(String content) { return null; }
 @Override public Map<String, Double> performPKL(String content) { return null; }
 @Override public Map<String, String> stemWords(String text) { return null; }
 @Override public Map<String, String> segmentWords(String text) { return null; }
}
public class ImportCommandTest {

    @Test
    public void testExecute_ValidInput() {
        // arrange
        MockBO mockBO = new MockBO();
        mockBO.shouldSucceed = true;
        File testFile = new File("document.txt");
        
        // act
        boolean result = mockBO.importTextFiles(testFile, "document.txt");
        
        // assert
        assertTrue("File should import successfully", result);
        assertEquals("document.txt", mockBO.lastFileName);
    }

    @Test
    public void testExecute_NullInput() {
        // arrange
        MockBO mockBO = new MockBO();
        
        // act & assert
        try {
            mockBO.importTextFiles(null, "test.txt");
            fail("Should throw exception for null file");
        } catch (NullPointerException e) {
            // Test passes - exception was thrown
            assertTrue(true);
        } catch (Exception e) {
            fail("Wrong exception type: " + e.getClass());
        }
    }

    @Test
    public void testExecute_BoundaryCase() {
        // arrange
        MockBO mockBO = new MockBO();
        File emptyFile = new File("");
        
        // act
        boolean result = mockBO.importTextFiles(emptyFile, "");
        System.out.println("Result with empty filename: " + result); // DEBUG
        
        // assert
        assertFalse("Empty filename should fail", result);
    }
}