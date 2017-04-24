package fr.gwombat.predicadmin.highchart.serie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.ChartType;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class AreaSplineSerie extends AreaRangeSerie {

    protected AreaSplineSerie(ChartType chartType) {
        super(chartType);
    }

    public AreaSplineSerie() {
        super(ChartType.AREASPLINE);
    }
    
    public AreaSplineSerie(String name){
        super(ChartType.AREASPLINE);
        super.setName(name);
    }

}
