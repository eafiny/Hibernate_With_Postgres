package ru.geekbrains.WithPostgres;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class MainApp {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            init();
            forcePrepareData();
            ProductDao.findById(1L, factory);
            ProductDao.findAll(factory);
            ProductDao.deleteById(2L, factory);
            ProductDao.saveOrUpdate(new Product("camera", 800), factory);
        }finally{
            shutDown();
        }
    }

    public static void init() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public static void shutDown(){
        factory.close();
    }

    public static void forcePrepareData() {
        Session session = null;
        try {
            String sql = Files.lines(Paths.get("full.sql")).collect(Collectors.joining(" "));
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                System.out.println("");
                session.close();
            }
        }
    }

}
