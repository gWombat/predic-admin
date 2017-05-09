package fr.gwombat.predicadmin.support;

public enum Privilege {
    ELDER("general.elder"),
    MINISTERIAL_SERVANT("general.ministerial.servant")
    ;
    
    private final String code;
    
    private Privilege(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    
    
}
