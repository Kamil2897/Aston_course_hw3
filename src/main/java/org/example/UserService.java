package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserService {
    static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static UserDAO userDAO = new UserDAO();
    static Scanner scanner = new Scanner(System.in);

    public static void menu() {
        logger.info("Меню:");
        System.out.println("1. Создать пользователя");
        System.out.println("2. Найти пользователя");
        System.out.println("3. Обновить данные пользователя");
        System.out.println("4. Удалить пользователя");
        System.out.println("5. Завершить работу");
    }

    public static void createUser() {
        User user = new User();
        logger.info("Введите имя нового пользователя:");
        user.setName(scanner.nextLine());
        logger.info("Введите электронную почту:");
        user.setEmail(scanner.nextLine());
        logger.info("Введите возраст:");
        try {
            user.setAge(scanner.nextInt());
        } catch (IllegalArgumentException e) {
            logger.warn("Ошибка ввода: необходимо ввести корректное значение возраста!");
            return;
        }
        Integer id = userDAO.create(user);
        if (id != 0) {
            logger.info("Пользователь с ID " + id + " успешно создан!");
        }
    }

    public static void readUser() {
        logger.info("Введите ID пользователя:");
        User user = userDAO.read(scanner.nextInt());
        logger.info("Имя пользователя: " + user.getName());
        logger.info("Электронная почта: " + user.getEmail());
        logger.info("Возраст: " + user.getAge());
        logger.info("Дата создания записи: " + user.getCreatedAt());
    }

    public static void updateUser() {
        User user = new User();
        logger.info("Введите ID пользователя:");
        user.setId(scanner.nextInt());
        scanner.nextLine();
        logger.info("Введите имя:");
        user.setName(scanner.nextLine());
        logger.info("Введите электронную почту:");
        user.setEmail(scanner.nextLine());
        logger.info("Введите возраст:");
        user.setAge(scanner.nextInt());
        if (userDAO.update(user)) {
            logger.info("Данные успешно обновлены!");
        }
    }

    public static void deleteUser() {
        logger.info("Введите ID пользователя");
        Integer id = scanner.nextInt();
        User user = userDAO.read(id);
        userDAO.delete(user);
        logger.info("Пользователь с ID " + id + " успешно удален!");
    }
}
