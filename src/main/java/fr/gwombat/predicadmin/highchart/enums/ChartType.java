package fr.gwombat.predicadmin.highchart.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ChartType {
    
    AREA,
    AREARANGE,
    AREASPLINE,
    AREASPLINERANGE,
    BAR,
    BOXPLOT,
    BUBBLE,
    COLUMN,
    COLUMNRANGE,
    ERRORBAR,
    FUNNEL,
    GAUGE,
    HEATMAP,
    LINE,
    PIE,
    POLYGON,
    PYRAMID,
    SCATTER,
    SOLIDGAUGE,
    SPLINE,
    TREEMAP,
    WATERFALL
    ;
    
    @JsonValue
    public String getType(){
        return this.name().toLowerCase();
    }

}
