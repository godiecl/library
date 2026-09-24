/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.service;

import cl.ucn.disc.arqsist.library.dao.BookDao;
import cl.ucn.disc.arqsist.library.dao.LoanDao;
import cl.ucn.disc.arqsist.library.model.Book;
import cl.ucn.disc.arqsist.library.model.Loan;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public final class LoanService {

    public static final int DUE_DAYS = 21;

    private final LoanDao loanDao;
    private final BookDao bookDao;

    public LoanService(LoanDao loanDao, BookDao bookDao) {
        this.loanDao = loanDao;
        this.bookDao = bookDao;
    }

    public List<Loan> findAll() throws SQLException {
        return loanDao.findAll();
    }

    public Loan returnLoan(int loanId) throws SQLException {
        Loan loan = loanDao.findById(loanId);
        if (loan == null || loan.isReturned()) {
            return loan;
        }

        loan.setReturned(true);
        loan.setReturnDate(LocalDate.now());

        LocalDate due = loan.getDueDate();
        LocalDate today = LocalDate.now();

        // Calculate overdue fee if the book is returned after the due date
        if (today.isAfter(loan.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(due, today);
            loan.setOverdueFee(daysOverdue * 1.0);
        }

        loanDao.update(loan);

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookDao.update(book);

        return loan;
    }

    /**
     * Find all overdue loans.
     *
     * @return a list of all overdue loans.
     */
    public List<Loan> overdueLoans() throws SQLException {
        LocalDate today = LocalDate.now();

        // Filter the loans to find those that are overdue
        return loanDao.findAll().stream()
                .filter(loan -> !loan.isReturned()) // Only consider loans that have not been returned
                .filter(loan -> loan.getDueDate().isBefore(today)) // Only consider loans that are overdue
                .toList();
    }
}
