/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.dao;

import cl.ucn.disc.arqsist.library.model.Book;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public final class BookDao {

    private final Dao<Book, Integer> dao;

    public BookDao(ConnectionSource connectionSource) throws SQLException {
        this.dao = DaoManager.createDao(connectionSource, Book.class);
    }

    public List<Book> findAll() throws SQLException {
        return dao.queryForAll();
    }

    public Book findById(int id) throws SQLException {
        return dao.queryForId(id);
    }

    public void create(Book book) throws SQLException {
        dao.create(book);
    }

    public void update(Book book) throws SQLException {
        dao.update(book);
    }

    public void delete(Book book) throws SQLException {
        dao.delete(book);
    }
}
