package org.example;

import org.slf4j.Logger;

public class UserService {

    private final Logger logger;
    private UserDAO userDAO;

    public UserService(Logger logger, UserDAO userDAO) {
        this.logger = logger;
        this.userDAO = userDAO;
    }

    public void createUser(User user) {
        Integer id = userDAO.create(user);
        if (id != 0) {
            logger.info("Пользователь с ID " + id + " успешно создан!");
        } else {
            logger.error("Невозможно создать пользователя: данный email уже используется!");
        }
    }

    public User readUser(int id) {
        User user = userDAO.read(id);
        if(user == null) {
            logger.error("Пользователя с ID " + id + " не существует!");
            return null;
        }
        logger.info("Имя пользователя: " + user.getName());
        logger.info("Электронная почта: " + user.getEmail());
        logger.info("Возраст: " + user.getAge());
        logger.info("Дата создания записи: " + user.getCreatedAt());
        return user;
    }

    public void updateUser(User user) {
        if (userDAO.update(user)) {
            logger.info("Данные успешно обновлены!");
        } else {
            logger.error("Невозможно обновить данные: данный email уже используется!");
        }
    }

    public void deleteUser(int id) {
        User user = userDAO.read(id);
        if(user != null) {
            userDAO.delete(user);
            logger.info("Пользователь с ID " + id + " успешно удален!");
        } else {
            logger.error("Пользователя с ID " + id + " не существует!");
        }
    }
}
