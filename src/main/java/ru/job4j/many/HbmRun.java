package ru.job4j.many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Car one = Car.of("Corvette");
            Car two = Car.of("Cruze");
            Car three = Car.of("Impala");
            Car four = Car.of("Tahoe");
            Car five = Car.of("Camaro");

            session.save(one);
            session.save(two);
            session.save(three);
            session.save(four);
            session.save(five);

            Brand chevrolet = Brand.of("Chevrolet");
            chevrolet.addCar(session.load(Car.class, 1));
            chevrolet.addCar(session.load(Car.class, 2));
            chevrolet.addCar(session.load(Car.class, 3));
            chevrolet.addCar(session.load(Car.class, 4));
            chevrolet.addCar(session.load(Car.class, 5));

            session.save(chevrolet);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}