package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Created by gWombat.
 *
 * @since 11/04/2017
 */
@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class Point {

    private String name;
    private String color;
    private Number x;
    private Number y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
