package fr.gwombat.predicadmin.highchart.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VerticalAlignment {
    
    TOP,
    MIDDLE,
    BOTTOM;
    
    @JsonValue
    public String getAlignment(){
        return this.name().toLowerCase();
    }

}
