package ru.job4j.manytomany;

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

            Author one = Author.of("Кей Хорстманн");
            Author two = Author.of("Герберт Шилдт");
            Author three = Author.of("Кети Сьерра");
            Author four = Author.of("Берт Бейтс");

            Book first = Book.of("Java. Библиотека профессионала. Том 1");
            first.getAuthors().add(one);

            Book second = Book.of("Java. Библиотека профессионала. Том 2");
            second.getAuthors().add(one);

            Book third = Book.of("Java. Полное руководство");
            third.getAuthors().add(two);

            Book fourth = Book.of("Java. Руководство для начинающих");
            fourth.getAuthors().add(three);
            fourth.getAuthors().add(four);

            session.persist(first);
            session.persist(second);
            session.persist(third);
            session.persist(fourth);

            Book book = session.get(Book.class, 4);
            session.remove(book);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}