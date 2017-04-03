package fr.gwombat.predicadmin.support;

public enum Gender {

    MALE("general.sex.male"), 
    FEMALE("general.sex.female")

    ;

    private final String labelCode;

    Gender(final String labelCode) {
        this.labelCode = labelCode;
    }

    public String getLabelCode() {
        return labelCode;
    }

}
