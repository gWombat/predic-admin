package fr.gwombat.predicadmin.web.alert;

public class SuccessAlertMessage extends AlertMessage {

    public SuccessAlertMessage() {
        super(AlertMessageLevel.SUCCESS);
    }

    public SuccessAlertMessage(final String labelCode) {
        super(AlertMessageLevel.SUCCESS);
        setLabelCode(labelCode);
    }

}
