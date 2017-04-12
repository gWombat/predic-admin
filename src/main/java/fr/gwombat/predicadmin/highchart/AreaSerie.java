package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.ChartType;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class AreaSerie extends Serie {

    private final ChartType type      = ChartType.AREA;
    private Number          lineWidth = 2;
    
    public AreaSerie() {
    }
    
    public AreaSerie(final String name) {
        super(name);
    }

    public Number getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Number lineWidth) {
        this.lineWidth = lineWidth;
    }

    public ChartType getType() {
        return type;
    }

}
