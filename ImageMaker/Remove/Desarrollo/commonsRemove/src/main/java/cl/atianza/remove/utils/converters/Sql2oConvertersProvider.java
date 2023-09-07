package cl.atianza.remove.utils.converters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import org.sql2o.converters.Converter;
import org.sql2o.converters.ConvertersProvider;

/**
 * Converter provider class for Sql2o.
 * @author pilin
 *
 */
public class Sql2oConvertersProvider implements ConvertersProvider {
	@Override
    public void fill(Map<Class<?>, Converter<?>> mapToFill) {
        mapToFill.put(LocalDate.class, new LocalDateConverter());
        mapToFill.put(LocalDateTime.class, new LocalDateTimeConverter());
        mapToFill.put(LocalTime.class, new LocalTimeConverter());
    }
}
