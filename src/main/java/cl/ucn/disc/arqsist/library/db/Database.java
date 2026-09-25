/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.db;

import cl.ucn.disc.arqsist.library.model.Book;
import cl.ucn.disc.arqsist.library.model.Loan;
import cl.ucn.disc.arqsist.library.model.Member;
import cl.ucn.disc.arqsist.library.model.Reservation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * The Database class for the Library application.
 */
public final class Database {

    /**
     * The connection source for the database.
     */
    private final ConnectionSource connectionSource;

    /**
     * Constructor for the Database class.
     *
     * @param jdbcUrl The JDBC URL for the database connection.
     * @throws SQLException if there is an error creating the connection or tables.
     */
    public Database(String jdbcUrl) throws SQLException {
        this.connectionSource = new JdbcConnectionSource(jdbcUrl);
        TableUtils.createTableIfNotExists(connectionSource, Book.class);
        TableUtils.createTableIfNotExists(connectionSource, Member.class);
        TableUtils.createTableIfNotExists(connectionSource, Loan.class);
        TableUtils.createTableIfNotExists(connectionSource, Reservation.class);
    }

    /**
     *
     * @return The connection source for the database.
     */
    public ConnectionSource connectionSource() {
        return connectionSource;
    }

    /**
     * Seeds the database with initial data if it is empty.
     *
     * @throws SQLException if there is an error accessing the database.
     */
    public void seedIfEmpty() throws SQLException {
        Dao<Book, Integer> bookDao = DaoManager.createDao(connectionSource, Book.class);

        // Seed the Book table if it is empty
        if (bookDao.queryForAll().isEmpty()) {
            bookDao.create(new Book("Clean Code", "Robert C. Martin", "9780132350884", 3));
            bookDao.create(new Book("The Pragmatic Programmer", "Hunt & Thomas", "9780201616224", 2));
            bookDao.create(new Book("Design Patterns", "Gamma et al.", "9780201633610", 4));
        }

        // Seed the Member table if it is empty
        Dao<Member, Integer> memberDao = DaoManager.createDao(connectionSource, Member.class);
        if (memberDao.queryForAll().isEmpty()) {
            memberDao.create(new Member("Ada Lovelace", "ada@example.com"));
            memberDao.create(new Member("Grace Hopper", "grace@example.com"));
            memberDao.create(new Member("Alan Turing", "alan@example.com"));
            memberDao.create(new Member("Edsger Dijkstra", "edsger@example.com"));
            memberDao.create(new Member("Barbara Liskov", "barbara@example.com"));
            memberDao.create(new Member("Donald Knuth", "donald@example.com"));
        }

        // Seed the Loan table if it is empty
        Dao<Loan, Integer> loanDao = DaoManager.createDao(connectionSource, Loan.class);
        if (loanDao.queryForAll().isEmpty()) {
            List<Book> books = bookDao.queryForAll();
            List<Member> members = memberDao.queryForAll();
            LocalDate today = LocalDate.now();

            loanDao.create(new Loan(members.getFirst(), books.getFirst(), today.minusDays(5), today.plusDays(9)));
            books.get(0).setAvailableCopies(books.get(0).getAvailableCopies() - 1);
            bookDao.update(books.get(0));

            loanDao.create(new Loan(members.get(1), books.get(2), today.minusDays(2), today.plusDays(12)));
            books.get(2).setAvailableCopies(books.get(2).getAvailableCopies() - 1);
            bookDao.update(books.get(2));

            loanDao.create(new Loan(members.get(2), books.get(1), today.minusDays(30), today.minusDays(9)));
            books.get(1).setAvailableCopies(books.get(1).getAvailableCopies() - 1);
            bookDao.update(books.get(1));
        }

        // Seed the Reservation table if it is empty
        Dao<Reservation, Integer> reservationDao = DaoManager.createDao(connectionSource, Reservation.class);
        if (reservationDao.queryForAll().isEmpty()) {
            List<Book> books = bookDao.queryForAll();
            List<Member> members = memberDao.queryForAll();
            LocalDate today = LocalDate.now();

            reservationDao.create(new Reservation(members.get(0), books.get(1), today.minusDays(1)));
            reservationDao.create(new Reservation(members.get(1), books.get(0), today.minusDays(3)));
        }
    }
}
