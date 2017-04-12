package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.AxisTitleAlignment;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class AxisTitle {

    private Number             margin;
    private Number             offset;
    private Number             rotation = 0;
    private Number             x        = 0;
    private Number             y;

    private String             text;

    private AxisTitleAlignment align    = AxisTitleAlignment.MIDDLE;

    public Number getMargin() {
        return margin;
    }

    public void setMargin(Number margin) {
        this.margin = margin;
    }

    public Number getOffset() {
        return offset;
    }

    public void setOffset(Number offset) {
        this.offset = offset;
    }

    public Number getRotation() {
        return rotation;
    }

    public void setRotation(Number rotation) {
        this.rotation = rotation;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AxisTitleAlignment getAlign() {
        return align;
    }

    public void setAlign(AxisTitleAlignment align) {
        this.align = align;
    }

}
