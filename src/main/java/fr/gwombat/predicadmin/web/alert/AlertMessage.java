package fr.gwombat.predicadmin.web.alert;

public abstract class AlertMessage {

    private String                  labelCode;
    private String                  icon;
    private String                  levelTextCode;
    private final AlertMessageLevel messageLevel;

    AlertMessage(final AlertMessageLevel messageLevel) {
        this.messageLevel = messageLevel;
        icon = messageLevel.getIcon();
        levelTextCode = messageLevel.getTextCode();
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    protected void setIcon(String icon) {
        this.icon = icon;
    }

    protected void setLevelTextCode(String levelTextCode) {
        this.levelTextCode = levelTextCode;
    }

    public String getIcon() {
        return icon;
    }

    public String getLevelTextCode() {
        return levelTextCode;
    }

    public String getMessageLevel() {
        return messageLevel.getLevel();
    }

}
