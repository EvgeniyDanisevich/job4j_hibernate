package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate candidate1 = Candidate.of("Иван", "Стажер", 50.0);
            Candidate candidate2 = Candidate.of("Василий", "Джуниор", 100.0);
            Candidate candidate3 = Candidate.of("Афанасий", "Миддл", 150.0);

            VacancyBase base = VacancyBase.of("hh.ru");
            Vacancy vacancy1 = Vacancy.of("Java junior", 100.00);
            Vacancy vacancy2 = Vacancy.of("Java middle", 150.00);

            base.setVacancies(List.of(vacancy1, vacancy2));
            candidate1.setVacancyBase(base);

            session.save(candidate1);
            session.save(candidate2);
            session.save(candidate3);
/*
            Query query1 = session.createQuery("from Candidate");
            for (Object st : query1.list()) {
                System.out.println(st);
            }

            System.out.println();

            Query query2 = session.createQuery("from Candidate c where c.id = :fId");
            query2.setParameter("fId", 1);
            System.out.println(query2.uniqueResult());

            System.out.println();

            Query query3 = session.createQuery("from Candidate c where c.name = :fName");
            query3.setParameter("fName", candidate2.getName());
            System.out.println(query3.uniqueResult());

            System.out.println();

            session.createQuery
                    ("update Candidate c set c.experience = :newExperience, c.salary = :newSalary where c.id = :fId")
                    .setParameter("newExperience", "Синьор")
                    .setParameter("newSalary", 200.0)
                    .setParameter("fId", 3)
                    .executeUpdate();

            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 2)
                    .executeUpdate();

            Query query4 = session.createQuery("from Candidate");
            for (Object st : query4.list()) {
                System.out.println(st);
            }
*/

            Candidate candidate = session.createQuery(
                    "select distinct c from Candidate c " +
                            "join fetch c.vacancyBase a " +
                            "join fetch a.vacancies where c.id = 1", Candidate.class).uniqueResult();

            System.out.println(candidate);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
