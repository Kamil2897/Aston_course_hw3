import org.example.User;
import org.example.UserDAO;
import org.example.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserDAO userDAO;
    @Mock
    private Logger logger;

    @Test
    void createUser_WhenIdIsNotZero(){
        User user = new User();
        Mockito.when(userDAO.create(user)).thenReturn(1);
        userService.createUser(user);
        Mockito.verify(userDAO).create(user);
        Mockito.verify(logger).info("Пользователь с ID 1 успешно создан!");
    }

    @Test
    void createUser_WhenIdIsZero(){
        User user = new User();
        Mockito.when(userDAO.create(user)).thenReturn(0);
        userService.createUser(user);
        Mockito.verify(userDAO).create(user);
        Mockito.verify(logger).error("Невозможно создать пользователя: данный email уже используется!");
    }

    @Test
    void readUser_WhenUserExists() {
        User user = new User();
        user.setName("Test");
        user.setEmail("Test@gmail.com");
        user.setAge(23);
        user.setCreatedAt(LocalDateTime.of(2025, 5, 29, 12, 0));
        Mockito.when(userDAO.read(1)).thenReturn(user);
        User user2 = userService.readUser(1);
        Mockito.verify(userDAO).read(1);
        Mockito.verify(logger).info("Имя пользователя: Test");
        Mockito.verify(logger).info("Электронная почта: Test@gmail.com");
        Mockito.verify(logger).info("Возраст: 23");
        Mockito.verify(logger).info("Дата создания записи: " + user.getCreatedAt());
        Assert.assertEquals(user, user2);
    }

    @Test
    void readUser_WhenUserIsNotExists() {
        Mockito.when(userDAO.read(1)).thenReturn(null);
        User user = userService.readUser(1);
        Mockito.verify(userDAO).read(1);
        Mockito.verify(logger).error("Пользователя с ID 1 не существует!");
        Assert.assertNull(user);
    }

    @Test
    void updateUser_WhenResultIsTrue() {
        User user = new User();
        Mockito.when(userDAO.update(user)).thenReturn(true);
        userService.updateUser(user);
        Mockito.verify(userDAO).update(user);
        Mockito.verify(logger).info("Данные успешно обновлены!");
    }

    @Test
    void updateUser_WhenResultIsFalse() {
        User user = new User();
        Mockito.when(userDAO.update(user)).thenReturn(false);
        userService.updateUser(user);
        Mockito.verify(userDAO).update(user);
        Mockito.verify(logger).error("Невозможно обновить данные: данный email уже используется!");
    }

    @Test
    void deleteUser_WhenUserExists() {
        User user = new User();
        Mockito.when(userDAO.read(1)).thenReturn(user);
        userService.deleteUser(1);
        Mockito.verify(userDAO).read(1);
        Mockito.verify(userDAO).delete(user);
        Mockito.verify(logger).info("Пользователь с ID 1 успешно удален!");
    }

    @Test
    void deleteUser_WhenUserIsNotExists() {
        Mockito.when(userDAO.read(1)).thenReturn(null);
        userService.deleteUser(1);
        Mockito.verify(userDAO).read(1);
        logger.error("Пользователя с ID 1 не существует!");
    }
}
