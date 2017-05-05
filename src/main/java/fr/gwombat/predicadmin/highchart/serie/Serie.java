package fr.gwombat.predicadmin.highchart.serie;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.Point;
import fr.gwombat.predicadmin.highchart.enums.ChartType;

/**
 * Created by gWombat.
 *
 * @since 11/04/2017
 */
@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class Serie {

    protected ChartType   type          = null;

    protected String      name;
    protected List<Point> data;
    protected Number      pointInterval = 1;
    protected Number      index;
    protected boolean     visible       = true;

    protected Serie(final ChartType chartType) {
        this.type = chartType;
    }

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

    public void addDataPoint(Point point) {
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public ChartType getType() {
        return type;
    }

    protected void setType(ChartType type) {
        this.type = type;
    }

    public Number getIndex() {
        return index;
    }

    public void setIndex(Number index) {
        this.index = index;
    }
}
