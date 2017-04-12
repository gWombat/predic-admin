package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class Credits {

    private boolean         enabled = true;
    private String          href    = "http://www.highcharts.com";
    private String          text    = "Highcharts.com";

    private CreditsPosition position;

    public Credits() {
        position = new CreditsPosition();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CreditsPosition getPosition() {
        return position;
    }

    public void setPosition(CreditsPosition position) {
        this.position = position;
    }
}
