package fr.gwombat.predicadmin.highchart.serie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.ChartType;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class LineSerie extends SplineSerie {

    protected LineSerie(ChartType chartType) {
        super(chartType);
    }

    public LineSerie() {
        super(ChartType.LINE);
    }

    public LineSerie(final String name) {
        this();
        super.setName(name);
    }

}
