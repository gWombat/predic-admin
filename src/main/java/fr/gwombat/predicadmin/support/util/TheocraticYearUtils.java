package fr.gwombat.predicadmin.support.util;

import fr.gwombat.predicadmin.model.TheocraticYear;

public final class TheocraticYearUtils {
    
    private TheocraticYearUtils() {
    }
    
    public static TheocraticYear getPreviousYear(final TheocraticYear year){
        if(year != null){
            return new TheocraticYear(year.getYear().getValue() - 1);
        }
        return null;
    }

}
