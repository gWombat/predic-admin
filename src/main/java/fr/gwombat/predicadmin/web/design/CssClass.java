package fr.gwombat.predicadmin.web.design;

public enum CssClass {

    TEXT_DANGER, 
    TEXT_SUCCESS,
    TEXT_GRAY,
    TEXT_GRAY_LIGHTER

    ;

    public String getValue() {
        return this.name().toLowerCase().replaceAll("_", "-");
    }

}
