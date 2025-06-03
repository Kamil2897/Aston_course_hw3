package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class Main {
    static final Logger logger = LoggerFactory.getLogger(Main.class);
    static final Logger loggerUserService = LoggerFactory.getLogger(UserService.class);
    public static void main(String[] args) {
        UserService userService = new UserService(loggerUserService, new UserDAO());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            logger.info("Меню:");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Найти пользователя");
            System.out.println("3. Обновить данные пользователя");
            System.out.println("4. Удалить пользователя");
            System.out.println("5. Завершить работу");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    scanner.nextLine();
                    User user = new User();
                    logger.info("Введите имя нового пользователя:");
                    user.setName(scanner.nextLine());
                    logger.info("Введите электронную почту:");
                    user.setEmail(scanner.nextLine());
                    logger.info("Введите возраст:");
                    try {
                        user.setAge(scanner.nextInt());
                        userService.createUser(user);
                    } catch (IllegalArgumentException e) {
                        logger.warn("Ошибка ввода: необходимо ввести корректное значение возраста!");
                    }
                } else if (choice == 2) {
                    logger.info("Введите ID пользователя:");
                    int id = scanner.nextInt();
                    userService.readUser(id);
                } else if (choice == 3) {
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
                    userService.updateUser(user);
                } else if (choice == 4) {
                    logger.info("Введите ID пользователя:");
                    int id = scanner.nextInt();
                    userService.deleteUser(id);
                } else if (choice == 5) {
                    break;
                } else {
                    logger.warn("Ошибка ввода: необходимо ввести целое число от 1 до 5!");
                }
            } else {
                logger.warn("Ошибка ввода: необходимо ввести число!");
                scanner.nextLine();
            }
        }
    }
}

