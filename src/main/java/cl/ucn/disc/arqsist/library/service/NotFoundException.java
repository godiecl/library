/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.service;

/**
 * Thrown when a record does not exist.
 */
public final class NotFoundException extends RuntimeException {

    /**
     * Creates the exception.
     *
     * @param message the detail message.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
