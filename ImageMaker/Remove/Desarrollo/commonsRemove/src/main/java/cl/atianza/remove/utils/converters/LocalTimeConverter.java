package cl.atianza.remove.utils.converters;

import java.sql.Time;
import java.time.LocalTime;

import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;

/**
 * Converter field for Sql2o parser for LocalTime class.
 * @author pilin
 *
 */
public class LocalTimeConverter implements Converter<LocalTime> {
	@Override
    public LocalTime convert(final Object val) throws ConverterException {
        if (val instanceof java.sql.Time) {
            return ((java.sql.Time) val).toLocalTime();
        } else {
            return null;
        }
    }

    @Override
    public Object toDatabaseParam(final LocalTime val) {
        if (val == null) {
            return null;
        } else {
            return Time.valueOf(val);
        }
    }
}
