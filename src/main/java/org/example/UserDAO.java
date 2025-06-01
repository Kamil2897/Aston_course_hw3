package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public UserDAO(){}

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int create(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(user);
                transaction.commit();
                return user.getId();
            } catch (Exception e) {
                transaction.rollback();
                logger.error("Невозможно создать пользователя: данный email уже используется!");
                return 0;
            }
        }
    }

    public User read(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    public boolean update(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(user);
                transaction.commit();
                return true;
            } catch (Exception e) {
                transaction.rollback();
                logger.error("Невозможно обновить данные: данный email уже используется!");
                return false;
            }
        }
    }

    public void delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.remove(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                logger.error("При попытке удаления произошла ошибка!");
            }
        }
    }
}
