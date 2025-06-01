package org.example;

import static org.example.UserService.logger;
import static org.example.UserService.scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        while (true) {
            userService.menu();
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    scanner.nextLine();
                    userService.createUser();
                } else if (choice == 2) {
                    userService.readUser();
                } else if (choice == 3) {
                    userService.updateUser();
                } else if (choice == 4) {
                    userService.deleteUser();
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

