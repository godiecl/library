/*
 * Copyright (c) 2026. Arquitectura de Sistemas, DISC, UCN, Antofagasta.
 */

package cl.ucn.disc.arqsist.library.db;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Persists a java.time.LocalDate as an ISO-8601 string.
 */
public final class LocalDatePersister extends BaseDataType {

    /**
     * The singleton instance.
     */
    private static final LocalDatePersister SINGLETON = new LocalDatePersister();

    /**
     * @return The singleton instance.
     */
    public static LocalDatePersister getSingleton() {
        return SINGLETON;
    }

    /**
     * The constructor.
     */
    private LocalDatePersister() {
        super(SqlType.STRING, new Class<?>[]{LocalDate.class});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return defaultStr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return LocalDate.parse((String) sqlArg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return javaObject.toString();
    }
}
