package fr.gwombat.predicadmin.highchart.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AxisCategory {
    
    LINEAR,
    LOGARITHMIC,
    DATETIME,
    CATEGORY;
    
    @JsonValue
    public String getCategory(){
        return this.name().toLowerCase();
    }

}
