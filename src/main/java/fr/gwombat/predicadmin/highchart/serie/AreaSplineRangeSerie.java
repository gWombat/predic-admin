package fr.gwombat.predicadmin.highchart.serie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.ChartType;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class AreaSplineRangeSerie extends AbstractLineAreaSerie {

    public AreaSplineRangeSerie() {
       super(ChartType.AREASPLINERANGE);
    }
    
    protected AreaSplineRangeSerie(ChartType chartType) {
        super(chartType);
    }

}
