package fr.gwombat.predicadmin.support.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.support.period.Period;

@Component
public class PeriodConverter implements Converter<String, Period> {

    private static final Logger logger = LoggerFactory.getLogger(PeriodConverter.class);

    @Override
    public Period convert(String source) {
        if (source == null)
            return null;

        try {
            final int parsedPeriod = Integer.parseInt(source);
            return Period.parseFromIntValue(parsedPeriod);
        } catch (NumberFormatException e) {
            logger.warn("Enable to parse value {} as integer", source);
        }
        return null;
    }

}
