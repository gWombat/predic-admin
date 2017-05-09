package fr.gwombat.predicadmin.web.design;

public enum Color {
    
    DANGER("#cb3e4b"),
    WARNING("#e66c40"),
    PERFUME("#f384fc"),
    EMINENCE("#732c86"),
    AQUAMARINE("#68fee0"),
    
    SPECIAL_WEEK(WARNING),
    MEMORIAL(DANGER),
    ELDER(AQUAMARINE),
    MINISTERIAL_SERVANT(PERFUME)
    
    ;
    
    private final String hexaColor;
    private final String cssClassName;
    
    private Color(Color otherColor) {
        this.hexaColor = otherColor.hexaColor;
        this.cssClassName = otherColor.name().toLowerCase();
    }
    
    private Color(final String hexaColor) {
        this.hexaColor = hexaColor;
        this.cssClassName = this.name().toLowerCase();
    }

    public String getHexaColor() {
        return hexaColor;
    }
    
    public String getCssClassName(){
        return cssClassName;
    }

}
