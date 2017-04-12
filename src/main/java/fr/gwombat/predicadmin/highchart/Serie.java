package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gWombat.
 *
 * @since 11/04/2017
 */
@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class Serie {

    private String      name;
    private List<Point> data;
    private Number      pointInterval = 1;

    public Serie() {
    }

    public Serie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addData(Point point) {
        if (data == null)
            data = new ArrayList<>(0);
        data.add(point);
    }

    public List<Point> getData() {
        return data;
    }

    public void setData(List<Point> data) {
        this.data = data;
    }

    public Number getPointInterval() {
        return pointInterval;
    }

    public void setPointInterval(Number pointInterval) {
        this.pointInterval = pointInterval;
    }
}
