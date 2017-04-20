package fr.gwombat.predicadmin.web.design;

public enum Color {
    
    DANGER("#cb3e4b"),
    WARNING("#e66c40"),
    
    SPECIAL_WEEK(WARNING),
    MEMORIAL(DANGER)
    
    ;
    
    private final String hexaColor;
    
    private Color(Color otherColor) {
        this.hexaColor = otherColor.hexaColor;
    }
    
    private Color(final String hexaColor) {
        this.hexaColor = hexaColor;
    }

    public String getHexaColor() {
        return hexaColor;
    }
    
    public String getCssClassName(){
        return this.name().toLowerCase();
    }

}
