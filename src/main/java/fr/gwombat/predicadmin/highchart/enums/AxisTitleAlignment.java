package fr.gwombat.predicadmin.highchart.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AxisTitleAlignment {
    
    LOW,
    MIDDLE,
    HIGH;
    
    @JsonValue
    public String getAlignment(){
        return this.name().toLowerCase();
    }

}
