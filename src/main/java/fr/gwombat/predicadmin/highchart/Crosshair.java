package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.DashStyle;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class Crosshair {

    private String    className;
    private String    color;

    private boolean   snap      = true;

    private Number    width;
    private Number    zIndex    = 2;

    private DashStyle dashStyle = DashStyle.SOLID;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isSnap() {
        return snap;
    }

    public void setSnap(boolean snap) {
        this.snap = snap;
    }

    public Number getWidth() {
        return width;
    }

    public void setWidth(Number width) {
        this.width = width;
    }

    public Number getzIndex() {
        return zIndex;
    }

    public void setzIndex(Number zIndex) {
        this.zIndex = zIndex;
    }

    public DashStyle getDashStyle() {
        return dashStyle;
    }

    public void setDashStyle(DashStyle dashStyle) {
        this.dashStyle = dashStyle;
    }

}
