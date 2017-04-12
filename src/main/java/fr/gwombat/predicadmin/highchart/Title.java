package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.HorizontalAlignment;
import fr.gwombat.predicadmin.highchart.enums.VerticalAlignment;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class Title {

    private boolean           floating    = false;
    private boolean           useHTML     = false;
    private Number            margin      = 15;
    private Number            widthAdjust = -44;
    private Number            x           = 0;
    private Number            y;
    private String            text        = "Chart title";

    private HorizontalAlignment    align       = HorizontalAlignment.CENTER;
    private VerticalAlignment verticalAlign;

    public HorizontalAlignment getAlign() {
        return align;
    }

    public void setAlign(HorizontalAlignment align) {
        this.align = align;
    }

    public boolean isFloating() {
        return floating;
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    public Number getMargin() {
        return margin;
    }

    public void setMargin(Number margin) {
        this.margin = margin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUseHTML() {
        return useHTML;
    }

    public void setUseHTML(boolean useHTML) {
        this.useHTML = useHTML;
    }

    public VerticalAlignment getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlignment verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public Number getWidthAdjust() {
        return widthAdjust;
    }

    public void setWidthAdjust(Number widthAdjust) {
        this.widthAdjust = widthAdjust;
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
