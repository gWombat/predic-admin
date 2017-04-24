package fr.gwombat.predicadmin.highchart.serie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.ChartType;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class AreaSerie extends AreaSplineSerie {

    public AreaSerie() {
        super(ChartType.AREA);
        super.lineWidth = 2;
    }

    public AreaSerie(final String name) {
        super(ChartType.AREA);
        super.setName(name);
        this.lineWidth = 2;
    }
}
