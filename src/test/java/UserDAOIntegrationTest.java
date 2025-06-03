import org.example.User;
import org.example.UserDAO;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class UserDAOIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
            .withDatabaseName("testDB")
            .withUsername("test")
            .withPassword("test");

    static SessionFactory sessionFactory;
    static UserDAO userDAO;
    private static final Logger logger = null;

    @BeforeAll
    static void setup() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
        userDAO = new UserDAO(sessionFactory);
    }

    @AfterAll
    static void endSession() {
        sessionFactory.close();
    }

    @Test
    void create() {
        User user = new User();
        user.setName("Test");
        user.setEmail("Test@gmail.com");
        user.setAge(21);
        int id = userDAO.create(user);
        Assert.assertTrue(id > 0);
        Assert.assertEquals("Test@gmail.com", userDAO.read(id).getEmail());
    }

    @Test
    void read_WhenUserExists() {
        User user = new User();
        user.setName("Test2");
        user.setEmail("Test2@gmail.com");
        user.setAge(22);
        int id = userDAO.create(user);
        User created = userDAO.read(id);
        Assert.assertEquals("Test2@gmail.com", created.getEmail());
    }

    @Test
    void read_WhenUserIsNotExists() {
        User created = userDAO.read(100);
        Assert.assertNull(created);
    }

    @Test
    void update() {
        User user = new User();
        user.setName("Test3");
        user.setEmail("Test3@gmail.com");
        user.setAge(23);
        int id = userDAO.create(user);
        user.setName("Test33");
        userDAO.update(user);
        User updated = userDAO.read(id);
        Assert.assertEquals("Test33", updated.getName());
    }

    @Test
    void delete() {
        User user = new User();
        user.setName("Test4");
        user.setEmail("Test4@gmail.com");
        user.setAge(24);
        int id = userDAO.create(user);
        userDAO.delete(user);
        Assert.assertNull(userDAO.read(id));
    }
}

