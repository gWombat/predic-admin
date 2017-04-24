package fr.gwombat.predicadmin.highchart.serie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.ChartType;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class AreaRangeSerie extends AreaSplineRangeSerie {

    protected AreaRangeSerie(ChartType chartType) {
        super(chartType);
    }

    public AreaRangeSerie() {
        super(ChartType.AREARANGE);
    }

}
