package com.example.hubject.repositories;

import com.example.hubject.models.Zipcode;
import com.example.hubject.repositories.contracts.ZipcodeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ZipcodeRepositoryImpl implements ZipcodeRepository {
    private final SessionFactory sessionFactory;

    public ZipcodeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Zipcode> getZipcodeById(int zipcodeId) {

        try (Session session = sessionFactory.openSession()) {
            Query<Zipcode> query = session.createQuery("FROM Zipcode as z where z.id = :zipcodeId", Zipcode.class);
            query.setParameter("zipcodeId", zipcodeId);
            List<Zipcode> result = query.list();

            if (result.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(result.get(0));
            }
        }
    }
    @Override
    public Optional<Zipcode> getZipcodeByValue(int zipcode) {

        try (Session session = sessionFactory.openSession()) {
            Query<Zipcode> query = session.createQuery("FROM Zipcode as z where z.zipcode = :zipcode", Zipcode.class);
            query.setParameter("zipcode", zipcode);
            List<Zipcode> result = query.list();

            if (result.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(result.get(0));
            }
        }
    }

    @Override
    public void addZipcode(Zipcode zipcode) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(zipcode);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateZipcode(Zipcode zipcode) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(zipcode);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteZipCode(Zipcode zipcode) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(zipcode);
            session.getTransaction().commit();
        }

    }
}

