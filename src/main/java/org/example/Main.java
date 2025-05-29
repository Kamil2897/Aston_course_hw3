package org.example;

import static org.example.UserService.logger;
import static org.example.UserService.scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            UserService.menu();
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if (choice == 1) {
                    scanner.nextLine();
                    UserService.createUser();
                } else if (choice == 2) {
                    UserService.readUser();
                } else if (choice == 3) {
                    UserService.updateUser();
                } else if (choice == 4) {
                    UserService.deleteUser();
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

