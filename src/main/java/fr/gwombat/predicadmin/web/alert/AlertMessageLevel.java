package fr.gwombat.predicadmin.web.alert;

enum AlertMessageLevel {

    SUCCESS("fa-check", "general.success");

    private final String icon;
    private final String textCode;

    AlertMessageLevel(String icon, String textCode) {
        this.icon = icon;
        this.textCode = textCode;
    }

    public String getIcon() {
        return icon;
    }

    public String getTextCode() {
        return textCode;
    }
    
    public String getLevel(){
        return this.name().toLowerCase();
    }

}
