/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.model;

import cl.ucn.disc.arqsist.library.db.LocalDatePersister;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;

/**
 * The Reservation entity.
 */
@DatabaseTable(tableName = "reservations")
public final class Reservation {

    /**
     * The ID.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The member.
     */
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Member member;

    /**
     * The book.
     */
    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Book book;

    /**
     * The reservation date.
     */
    @DatabaseField(canBeNull = false, persisterClass = LocalDatePersister.class)
    private LocalDate reservedAt;

    /**
     * True if the reservation is fulfilled.
     */
    @DatabaseField
    private boolean fulfilled;

    /**
     * The empty constructor for ORMLite.
     */
    public Reservation() {
    }

    /**
     * The Constructor.
     *
     * @param member     The member.
     * @param book       The book.
     * @param reservedAt The reservation date.
     */
    public Reservation(Member member, Book book, LocalDate reservedAt) {
        this.member = member;
        this.book = book;
        this.reservedAt = reservedAt;
        this.fulfilled = false;
    }

    /**
     * @return The ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID.
     *
     * @param id The new ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The member.
     */
    public Member getMember() {
        return member;
    }

    /**
     * Set the member.
     *
     * @param member The new member.
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * @return The book.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Set the book.
     *
     * @param book The new book.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * @return The reservation date.
     */
    public LocalDate getReservedAt() {
        return reservedAt;
    }

    /**
     * Set the reservation date.
     *
     * @param reservedAt The new reservation date.
     */
    public void setReservedAt(LocalDate reservedAt) {
        this.reservedAt = reservedAt;
    }

    /**
     * @return True if the reservation is fulfilled.
     */
    public boolean isFulfilled() {
        return fulfilled;
    }

    /**
     * Set the fulfilled flag.
     *
     * @param fulfilled The new fulfilled flag.
     */
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
}
