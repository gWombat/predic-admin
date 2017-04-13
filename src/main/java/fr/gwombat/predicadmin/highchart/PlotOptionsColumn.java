package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class PlotOptionsColumn {

    private Number pointPadding = 0.1;
    private Number groupPadding = 0.2;
    private Number borderWidth = 1;

    public Number getPointPadding() {
        return pointPadding;
    }

    public void setPointPadding(Number pointPadding) {
        this.pointPadding = pointPadding;
    }

    public Number getGroupPadding() {
        return groupPadding;
    }

    public void setGroupPadding(Number groupPadding) {
        this.groupPadding = groupPadding;
    }

    public Number getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(Number borderWidth) {
        this.borderWidth = borderWidth;
    }

}
