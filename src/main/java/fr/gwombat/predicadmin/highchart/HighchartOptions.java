package fr.gwombat.predicadmin.highchart;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class HighchartOptions {

    private Chart       chart;
    private Title       title;
    private Axis        xAxis;
    private Axis        yAxis;
    private Credits     credits;
    private Legend      legend;
    private Exporting   exporting;
    private List<Serie> series;

    public HighchartOptions() {
        chart = new Chart();
        title = new Title();
        credits = new Credits();
        legend = new Legend();
        exporting = new Exporting();
        series = new ArrayList<>(0);
        xAxis = new Axis();
        yAxis = new Axis();
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @JsonProperty("xAxis")
    public Axis getxAxis() {
        return xAxis;
    }

    public void setxAxis(Axis xAxis) {
        this.xAxis = xAxis;
    }

    @JsonProperty("yAxis")
    public Axis getyAxis() {
        return yAxis;
    }

    public void setyAxis(Axis yAxis) {
        this.yAxis = yAxis;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }

    public Exporting getExporting() {
        return exporting;
    }

    public void setExporting(Exporting exporting) {
        this.exporting = exporting;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

}
