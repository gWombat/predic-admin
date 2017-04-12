package fr.gwombat.predicadmin.highchart.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HorizontalAlignment {
    
    CENTER,
    LEFT,
    RIGHT;
    
    @JsonValue
    public String getAlignment(){
        return this.name().toLowerCase();
    }

}
