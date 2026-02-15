package data;

import static org.junit.Assert.*;
import org.junit.Test;

import dal.HashCalculator;

public class HashCalculatorTest {
    
    // ✅ TEST 1: Same text should produce same hash
    @Test
    public void testHash_SameText_SameHash() throws Exception {
        // Calculate hash for same text twice
        String hash1 = HashCalculator.calculateHash("Hello World");
        String hash2 = HashCalculator.calculateHash("Hello World");
        
        // Assert both hashes are the same
        assertEquals("Same text should produce same hash", hash1, hash2);
    }
    
    // ✅ TEST 2: Different text should have different hashes
    @Test
    public void testHash_DifferentText_DifferentHash() throws Exception {
        // Calculate hashes for different texts
        String hash1 = HashCalculator.calculateHash("Hello World");
        String hash2 = HashCalculator.calculateHash("Hello World!");
        
        // Assert hashes are different
        assertNotEquals("Different text should produce different hashes", hash1, hash2);
    }
    
    // ✅ TEST 3: Empty string hash
    @Test
    public void testHash_EmptyString() throws Exception {
        // Calculate hash for empty string
        String hash = HashCalculator.calculateHash("");
        
        // Assert hash is not null and not empty
        assertNotNull("Empty string should still produce a hash", hash);
        assertFalse("Hash should not be empty", hash.isEmpty());
        System.out.println("Empty string hash: " + hash);
    }
    
    // ✅ TEST 4: Null input
    @Test
    public void testHash_NullInput() {
        try {
            HashCalculator.calculateHash(null);
            fail("Should throw exception for null input");
        } catch (Exception e) {
            // Test passes - exception thrown
            assertTrue(true);
        }
    }
    
    // ✅ TEST 5: Hash integrity - small change should change hash significantly
    @Test
    public void testHash_SmallChange_BigDifference() throws Exception {
        // Calculate hash for original text
        String originalHash = HashCalculator.calculateHash("The quick brown fox jumps over the lazy dog");
        
        // Calculate hash for text with one character changed
        String modifiedHash = HashCalculator.calculateHash("The quick brown fox jumps over the lazy dog!");
        
        // Assert hashes are completely different
        assertNotEquals("Small change should produce completely different hash", originalHash, modifiedHash);
        
        System.out.println("Original hash: " + originalHash);
        System.out.println("Modified hash: " + modifiedHash);
    }
    
    // ✅ TEST 6: Hash length should be consistent (MD5 produces 32 characters)
    @Test
    public void testHash_Length() throws Exception {
        String hash = HashCalculator.calculateHash("Any text here");
        
        // MD5 hash is always 32 characters (hex representation)
        assertEquals("MD5 hash should be 32 characters", 32, hash.length());
    }
    
    // ✅ TEST 7: Unicode/UTF-8 text support
    @Test
    public void testHash_UnicodeText() throws Exception {
        // Test with Arabic text
        String arabicText = "مرحبا بالعالم";
        String hash1 = HashCalculator.calculateHash(arabicText);
        
        // Same Arabic text should produce same hash
        String hash2 = HashCalculator.calculateHash(arabicText);
        
        assertEquals("Unicode text should hash consistently", hash1, hash2);
        System.out.println("Arabic text hash: " + hash1);
    }
}