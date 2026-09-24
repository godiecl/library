/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.model;

import cl.ucn.disc.arqsist.library.db.LocalDatePersister;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;

/**
 * The Loan entity.
 */
@DatabaseTable(tableName = "loans")
public final class Loan {

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
     * The loan date.
     */
    @DatabaseField(canBeNull = false, persisterClass = LocalDatePersister.class)
    private LocalDate loanDate;

    /**
     * The due date.
     */
    @DatabaseField(canBeNull = false, persisterClass = LocalDatePersister.class)
    private LocalDate dueDate;

    /**
     * The return date, or null if the loan is not returned.
     */
    @DatabaseField(persisterClass = LocalDatePersister.class)
    private LocalDate returnDate;

    /**
     * True if the loan is returned.
     */
    @DatabaseField
    private boolean returned;

    /**
     * The overdue fee.
     */
    @DatabaseField
    private double overdueFee;

    /**
     * The empty constructor for ORMLite.
     */
    public Loan() {
    }

    /**
     * The Constructor.
     *
     * @param member   The member.
     * @param book     The book.
     * @param loanDate The loan date.
     * @param dueDate  The due date.
     */
    public Loan(Member member, Book book, LocalDate loanDate, LocalDate dueDate) {
        this.member = member;
        this.book = book;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returned = false;
        this.overdueFee = 0.0;
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
     * @return The loan date.
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     * Set the loan date.
     *
     * @param loanDate The new loan date.
     */
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * @return The due date.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date.
     *
     * @param dueDate The new due date.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return The return date, or null if the loan is not returned.
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Set the return date.
     *
     * @param returnDate The new return date.
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return True if the loan is returned.
     */
    public boolean isReturned() {
        return returned;
    }

    /**
     * Set the returned flag.
     *
     * @param returned The new returned flag.
     */
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    /**
     * @return The overdue fee.
     */
    public double getOverdueFee() {
        return overdueFee;
    }

    /**
     * Set the overdue fee.
     *
     * @param overdueFee The new overdue fee.
     */
    public void setOverdueFee(double overdueFee) {
        this.overdueFee = overdueFee;
    }
}
