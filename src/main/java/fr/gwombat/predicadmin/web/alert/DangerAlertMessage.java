package fr.gwombat.predicadmin.web.alert;

public class DangerAlertMessage extends AlertMessage {
    
    public DangerAlertMessage() {
        super(AlertMessageLevel.DANGER);
    }
    
    public DangerAlertMessage(final String labelCode) {
        super(AlertMessageLevel.DANGER);
        setLabelCode(labelCode);
    }

}
