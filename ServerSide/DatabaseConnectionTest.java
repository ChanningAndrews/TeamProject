//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ServerSide;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {
    private Database database;

    DatabaseConnectionTest() {
    }

    @BeforeEach
    void setUp() {
        this.database = new Database();
    }

    @AfterEach
    void tearDown() {
        this.database.closeConnection();
    }

    @Test
    void testConnection() {
        Assertions.assertNotNull(this.database, "Database should be initialized.");
    }

    @Test
    void testAddAndRetrieveUser() {
        User testUser = new User("testUser", "password123", 0, 0);
        Assertions.assertTrue(this.database.addUser(testUser), "User should be added successfully.");
        User retrievedUser = this.database.getUser("testUser");
        Assertions.assertNotNull(retrievedUser, "User should be retrieved successfully.");
        Assertions.assertEquals("testUser", User.getUsername());
    }

    @Test
    void testDeleteUser() {
        User testUser = new User("testDelete", "password123", 0, 0);
        this.database.addUser(testUser);
        this.database.removeUser("testDelete");
        Assertions.assertNull(this.database.getUser("testDelete"), "User should be removed.");
    }

    @Test
    void testValidateCredentials() {
        User testUser = new User("validUser", "password123", 0, 0);
        this.database.addUser(testUser);
        Assertions.assertTrue(this.database.validateCredentials("validUser", "password123"), "Credentials should be valid.");
        Assertions.assertFalse(this.database.validateCredentials("validUser", "wrongPassword"), "Invalid password should fail.");
    }
}
