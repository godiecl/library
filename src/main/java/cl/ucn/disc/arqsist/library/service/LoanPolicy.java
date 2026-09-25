/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.service;

import java.time.LocalDate;

/**
 * Holds the loan policy: the loan period and the fee rate.
 */
public final class LoanPolicy {

    /**
     * The loan period in days.
     */
    public static final int DUE_DAYS = 21;

    /**
     * The fee for each day after the due date.
     */
    public static final double FEE_PER_DAY = 1.0;

    /**
     * Private constructor.
     */
    private LoanPolicy() {
        // nothing here.
    }

    /**
     * Computes the due date of a loan.
     *
     * @param loanDate the date of the loan.
     * @return the due date.
     */
    public static LocalDate computeDueDate(LocalDate loanDate) {
        return loanDate.plusDays(DUE_DAYS);
    }
}
