package com.example.hubject.repositories;

import com.example.hubject.models.ChargingStation;
import com.example.hubject.repositories.contracts.ChargingStationRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChargingStationRepositoryImpl implements ChargingStationRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public ChargingStationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ChargingStation> getAllChargingStations() {
        try (Session session = sessionFactory.openSession()) {
            Query<ChargingStation> query = session.createQuery("SELECT c from ChargingStation c", ChargingStation.class);
            return query.list();
        }
    }

    @Override
    public Optional<ChargingStation> getChargingStationById(int chargingStationId) {
        try (Session session = sessionFactory.openSession()) {
            Query<ChargingStation> query = session.createQuery("FROM ChargingStation as c where c.id = :id", ChargingStation.class);
            query.setParameter("id", chargingStationId);
            return Optional.ofNullable(query.uniqueResult());
        }
    }

    @Override
    public Optional<List<ChargingStation>> getChargingStationByZipcode(int zipcode) {
        try (Session session = sessionFactory.openSession()) {
            Query<ChargingStation> query = session.createQuery("FROM ChargingStation as c where c.zipcode.zipcode = :zipcode", ChargingStation.class);
            query.setParameter("zipcode", zipcode);
            List<ChargingStation> result = query.list();
            return Optional.ofNullable(result.isEmpty() ? null : result);
        }
    }

    @Override
    public Optional<ChargingStation> getChargingStationByGeolocation(double latitude, double longitude) {
        try (Session session = sessionFactory.openSession()) {
            Query<ChargingStation> query = session.createQuery("FROM ChargingStation as c where c.latitude = :latitude " +
                    "and c.longitude= :longitude", ChargingStation.class);
            query.setParameter("latitude", latitude);
            query.setParameter("longitude", longitude);
            return Optional.ofNullable(query.uniqueResult());
        }
    }

    @Override
    public void addChargingStation(ChargingStation chargingStation) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(chargingStation);
            session.getTransaction().commit();
        }

    }

    @Override
    public void updateChargingStation(ChargingStation chargingStation) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(chargingStation);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteChargingStation(int chargingStationId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(chargingStationId);
            session.getTransaction().commit();
        }

    }
}
