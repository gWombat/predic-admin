package fr.gwombat.predicadmin.highchart.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DashStyle {
    
    SOLID,
    SHORTDASH,
    SHORTDOT,
    SHORTDASHDOT,
    SHORTDASHDOTDOT,
    DOT,
    DASH,
    LONGDASH,
    DASHDOT,
    LONGDASHDOT,
    LONGDASHDOTDOT
    ;
    
    @JsonValue
    public String getStyle(){
        return this.name().toLowerCase();
    }

}
