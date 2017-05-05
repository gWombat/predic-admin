package fr.gwombat.predicadmin.web.vo;

import java.text.NumberFormat;

import fr.gwombat.predicadmin.web.design.CssClass;

public class PercentageNumberElementVO extends NumberElementVO<Double> {

    public PercentageNumberElementVO(Double value) {
        super(value);
    }

    public String getFormattedValue() {
        if (value == null)
            return null;

        final NumberFormat format = NumberFormat.getPercentInstance();
        format.setMinimumFractionDigits(1);
        format.setMaximumFractionDigits(2);

        return format.format(value);
    }

    public String getCssClass() {
        if (value == null)
            return null;

        if (value.doubleValue() > 0.01)
            return CssClass.TEXT_SUCCESS.getValue();
        else if (value.doubleValue() >= 0 && value.doubleValue() <= 0.01)
            return CssClass.TEXT_GRAY_LIGHTER.getValue();

        return CssClass.TEXT_DANGER.getValue();
    }

}
