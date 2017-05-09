package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.support.Privilege;
import fr.gwombat.predicadmin.web.design.Color;

public class PrivilegeVO {

    private final String cssClass;
    private final String labelCode;

    public PrivilegeVO(final Privilege privilege) {
        cssClass = initCssClass(privilege);
        labelCode = privilege.getCode();
    }

    private String initCssClass(final Privilege privilege) {
        switch (privilege) {
        case ELDER:
            return Color.ELDER.getCssClassName();
        case MINISTERIAL_SERVANT:
            return Color.MINISTERIAL_SERVANT.getCssClassName();
        default:
            return null;
        }
    }

    public String getCssClass() {
        return cssClass;
    }

    public String getLabelCode() {
        return labelCode;
    }

}
