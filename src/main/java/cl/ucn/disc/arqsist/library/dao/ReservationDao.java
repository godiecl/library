/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.dao;

import cl.ucn.disc.arqsist.library.model.Reservation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public final class ReservationDao {

    private final Dao<Reservation, Integer> dao;

    public ReservationDao(ConnectionSource connectionSource) throws SQLException {
        this.dao = DaoManager.createDao(connectionSource, Reservation.class);
    }

    public List<Reservation> findAll() throws SQLException {
        return dao.queryForAll();
    }

    public Reservation findById(int id) throws SQLException {
        return dao.queryForId(id);
    }

    public void create(Reservation reservation) throws SQLException {
        dao.create(reservation);
    }

    public void update(Reservation reservation) throws SQLException {
        dao.update(reservation);
    }
}
