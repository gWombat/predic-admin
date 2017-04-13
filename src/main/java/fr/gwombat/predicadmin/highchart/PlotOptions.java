package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class PlotOptions {

    private PlotOptionsColumn column;

    public PlotOptions() {
        column = new PlotOptionsColumn();
    }

    public PlotOptionsColumn getColumn() {
        return column;
    }

    public void setColumn(PlotOptionsColumn column) {
        this.column = column;
    }

}
