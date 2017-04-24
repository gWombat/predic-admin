package fr.gwombat.predicadmin.highchart.serie;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.ChartType;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public abstract class AbstractLineAreaSerie extends Serie {

    protected Number lineWidth;

    protected AbstractLineAreaSerie(ChartType chartType) {
        super(chartType);
    }

    public Number getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Number lineWidth) {
        this.lineWidth = lineWidth;
    }

}
