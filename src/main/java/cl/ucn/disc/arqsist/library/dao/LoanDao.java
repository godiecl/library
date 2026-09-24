/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.dao;

import cl.ucn.disc.arqsist.library.model.Loan;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public final class LoanDao {

    private final Dao<Loan, Integer> dao;

    public LoanDao(ConnectionSource connectionSource) throws SQLException {
        this.dao = DaoManager.createDao(connectionSource, Loan.class);
    }

    public List<Loan> findAll() throws SQLException {
        return dao.queryForAll();
    }

    public Loan findById(int id) throws SQLException {
        return dao.queryForId(id);
    }

    public void create(Loan loan) throws SQLException {
        dao.create(loan);
    }

    public void update(Loan loan) throws SQLException {
        dao.update(loan);
    }
}
