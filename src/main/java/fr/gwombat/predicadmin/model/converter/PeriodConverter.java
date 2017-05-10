package fr.gwombat.predicadmin.model.converter;

import javax.persistence.AttributeConverter;

import fr.gwombat.predicadmin.support.period.Period;

public class PeriodConverter implements AttributeConverter<Period, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Period period) {
        if(period == null)
            return null;
        return period.getPeriodValue();
    }

    @Override
    public Period convertToEntityAttribute(final Integer value) {
        return Period.parseFromIntValue(value);
    }

}
