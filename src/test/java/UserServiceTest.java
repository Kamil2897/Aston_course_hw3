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

import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserDAO userDAO;
    @Mock
    private Scanner scanner;
    @Mock
    private Logger logger;

    @Test
    void createUser(){
        User user = new User();
        Mockito.when(scanner.nextLine()).thenReturn("Test").thenReturn("Test@gmail.com");
        Mockito.when(scanner.nextInt()).thenReturn(21);
        Mockito.when(userDAO.create(user)).thenReturn(1);

        userService.createUser();

        Assert.assertEquals("Test", user.getName());
        Assert.assertEquals("Test@gmail.com", user.getEmail());
        Assert.assertEquals(21, user.getAge());

        Mockito.verify(logger).info("Введите имя нового пользователя:");
        Mockito.verify(logger).info("Введите электронную почту:");
        Mockito.verify(logger).info("Введите возраст:");
        Mockito.verify(logger).info("Пользователь с ID 1 успешно создан!");
    }
}
