package ru.job4j.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        List<Mark> marks;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Mark chevrolet = Mark.of("Chevrolet");

            LazyAuto one = LazyAuto.of("Corvette", chevrolet);
            LazyAuto two = LazyAuto.of("Cruze", chevrolet);
            LazyAuto three = LazyAuto.of("Impala", chevrolet);
            LazyAuto four = LazyAuto.of("Tahoe", chevrolet);
            LazyAuto five = LazyAuto.of("Camaro", chevrolet);

            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);

            session.save(chevrolet);

            marks = session.createQuery("from Mark").list();

            for (Mark mark : marks) {
                for (LazyAuto lazyAuto : mark.getAutos()) {
                    System.out.println(lazyAuto);
                }
            }

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}