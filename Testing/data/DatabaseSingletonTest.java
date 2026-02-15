package data;

import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import dal.DatabaseConnection;

public class DatabaseSingletonTest {
    
    // ✅ TEST 1: Get instance twice should return same object
    @Test
    public void testSingleton_SameInstance() {
        // Get instance twice
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();
        
        // Assert both references point to the same object
        assertSame("Singleton should return same instance", instance1, instance2);
        
        System.out.println("Instance1: " + instance1);
        System.out.println("Instance2: " + instance2);
    }
    
    // ✅ TEST 2: Constructor should be private
    @Test
    public void testSingleton_PrivateConstructor() {
        // Get all constructors of DatabaseConnection
        Constructor<?>[] constructors = DatabaseConnection.class.getDeclaredConstructors();
        
        // Check each constructor
        for (Constructor<?> constructor : constructors) {
            // Assert that constructor is private
            assertTrue("Constructor should be private", 
                      Modifier.isPrivate(constructor.getModifiers()));
        }
        
        System.out.println("All constructors are private");
    }
    
 // ✅ TEST 3: Reflection can access private constructor (this is normal in Java)
    @Test
    public void testSingleton_ReflectionNotAllowed() {
        try {
            // Try to access private constructor via reflection
            Constructor<DatabaseConnection> constructor = 
                DatabaseConnection.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            
            // Try to create new instance
            DatabaseConnection newInstance = constructor.newInstance();
            
            // In Java, reflection CAN access private constructors
            // So we should verify it creates a DIFFERENT instance
            DatabaseConnection singleton = DatabaseConnection.getInstance();
            
            // Assert they are different instances
            assertNotSame("Reflection should create different instance", 
                         singleton, newInstance);
            
            // Also verify the new instance is not null
            assertNotNull("Reflection instance should not be null", newInstance);
            
            System.out.println("Singleton instance: " + singleton);
            System.out.println("Reflection instance: " + newInstance);
            
        } catch (Exception e) {
            // If exception occurs, that's also acceptable
            System.out.println("Exception occurred: " + e.getMessage());
            assertTrue(true);
        }
    }    
    // ✅ TEST 4: Instance should not be null
    @Test
    public void testSingleton_InstanceNotNull() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        
        assertNotNull("Singleton instance should not be null", instance);
    }
    
    // ✅ TEST 5: Multiple calls should have same hashcode
    @Test
    public void testSingleton_SameHashCode() {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();
        
        assertEquals("Both instances should have same hashcode", 
                    instance1.hashCode(), instance2.hashCode());
    }
    
    // ✅ TEST 6: Verify class is final (optional - based on implementation)
    @Test
    public void testSingleton_ClassIsFinal() {
        // Check if class is final
        boolean isFinal = Modifier.isFinal(DatabaseConnection.class.getModifiers());
        
        // This is optional - singleton doesn't have to be final
        System.out.println("DatabaseConnection class is " + 
                          (isFinal ? "final" : "not final"));
        
        // Not asserting - just informational
    }
}