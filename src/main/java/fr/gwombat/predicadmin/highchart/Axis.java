package fr.gwombat.predicadmin.highchart;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import fr.gwombat.predicadmin.highchart.enums.AxisCategory;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class Axis {

    private AxisCategory         type = AxisCategory.LINEAR;
    private AxisTitle            title;
    private Crosshair            crosshair;
    private Number               tickInterval;
    private DateTimeLabelFormats dateTimeLabelFormats;
    private List<String>         categories;

    public Axis() {
        title = new AxisTitle();
        crosshair = new Crosshair();
        dateTimeLabelFormats = new DateTimeLabelFormats();
    }

    public AxisCategory getType() {
        return type;
    }

    public void setType(AxisCategory type) {
        this.type = type;
    }

    public AxisTitle getTitle() {
        return title;
    }

    public void setTitle(AxisTitle title) {
        this.title = title;
    }

    public Crosshair getCrosshair() {
        return crosshair;
    }

    public void setCrosshair(Crosshair crosshair) {
        this.crosshair = crosshair;
    }

    public void setCrosshair(boolean useCrosshair) {
        crosshair = useCrosshair ? new Crosshair() : null;
    }

    public Number getTickInterval() {
        return tickInterval;
    }

    public void setTickInterval(Number tickInterval) {
        this.tickInterval = tickInterval;
    }

    public DateTimeLabelFormats getDateTimeLabelFormats() {
        return dateTimeLabelFormats;
    }

    public void setDateTimeLabelFormats(DateTimeLabelFormats dateTimeLabelFormats) {
        this.dateTimeLabelFormats = dateTimeLabelFormats;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
