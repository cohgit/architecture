package cl.atianza.remove.utils.converters;

import java.sql.Date;
import java.time.LocalDate;

import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;

/**
 * Converter field for Sql2o parser for LocalDate class.
 * @author pilin
 *
 */
public class LocalDateConverter implements Converter<LocalDate> {
	@Override
    public LocalDate convert(final Object val) throws ConverterException {
        if (val instanceof java.sql.Date) {
            return ((java.sql.Date) val).toLocalDate();
        } else {
            return null;
        }
    }

    @Override
    public Object toDatabaseParam(final LocalDate val) {
        if (val == null) {
            return null;
        } else {
            return Date.valueOf(val);
        }
    }
}
