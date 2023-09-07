package cl.atianza.remove.utils.converters;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;

/**
 * Converter field for Sql2o parser for LocalDateTime class.
 * @author pilin
 *
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
	@Override
    public LocalDateTime convert(final Object val) throws ConverterException {
        if (val instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) val).toLocalDateTime();
        } else {
            return null;
        }
    }

    @Override
    public Object toDatabaseParam(final LocalDateTime val) {
        if (val == null) {
            return null;
        } else {
            return Timestamp.valueOf(val);
        }
    }
}
