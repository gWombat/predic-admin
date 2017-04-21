package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class Tooltip {
    
    private boolean shared = false;

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

}
