package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.HorizontalAlignment;
import fr.gwombat.predicadmin.highchart.enums.VerticalAlignment;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class CreditsPosition {

    private HorizontalAlignment align         = HorizontalAlignment.RIGHT;
    private VerticalAlignment   verticalAlign = VerticalAlignment.BOTTOM;
    private Number              x             = -10;
    private Number              y             = -5;

    public HorizontalAlignment getAlign() {
        return align;
    }

    public void setAlign(HorizontalAlignment align) {
        this.align = align;
    }

    public VerticalAlignment getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlignment verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public Number getX() {
        return x;
    }

    public void setX(Number x) {
        this.x = x;
    }

    public Number getY() {
        return y;
    }

    public void setY(Number y) {
        this.y = y;
    }
}
