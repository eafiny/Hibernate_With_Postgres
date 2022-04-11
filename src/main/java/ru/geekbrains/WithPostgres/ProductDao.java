package ru.geekbrains.WithPostgres;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static ru.geekbrains.WithPostgres.MainApp.shutDown;

public class ProductDao {
    private static SessionFactory factory;

    public static void findById(Long id, SessionFactory factory) {
           try(Session session = factory.getCurrentSession()){
                session.beginTransaction();
                Product product = session.get(Product.class, 1L);
                System.out.println(product);
                session.getTransaction().commit();
           }
    }

    public static void findAll(SessionFactory factory) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            List<Product> items = session.createQuery("from Product").getResultList();
            System.out.println(items + "\n");

            /*SimpleItem si1 = session.createQuery("select s from SimpleItem s where s.id = 3", SimpleItem.class).getSingleResult();
            System.out.println(si1 + "\n");

            List<SimpleItem> cheapItems = session.createQuery("select s from SimpleItem s where s.price < 80").getResultList();
            System.out.println(cheapItems + "\n");*/

            session.getTransaction().commit();
        }
    }

    public static void deleteById(Long id, SessionFactory factory) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    public static void saveOrUpdate(Product product, SessionFactory factory) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        }
    }
}
