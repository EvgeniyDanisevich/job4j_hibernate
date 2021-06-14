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

            Auto one = Auto.of("Corvette", chevrolet);
            Auto two = Auto.of("Cruze", chevrolet);
            Auto three = Auto.of("Impala", chevrolet);
            Auto four = Auto.of("Tahoe", chevrolet);
            Auto five = Auto.of("Camaro", chevrolet);

            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);

            session.save(chevrolet);

            marks = session.createQuery("from Mark").list();

            for (Mark mark : marks) {
                for (Auto auto : mark.getAutos()) {
                    System.out.println(auto);
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