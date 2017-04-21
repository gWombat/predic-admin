package fr.gwombat.predicadmin.highchart;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_DEFAULT, content = Include.NON_NULL)
public class ChartConfiguration {

    private Chart       chart;
    private Title       title;
    private Axis        xAxis;
    private Axis        yAxis;
    private Credits     credits;
    private Legend      legend;
    private Exporting   exporting;
    private List<Serie> series;
    private PlotOptions plotOptions;
    private Tooltip     tooltip;

    /**
     * Creates a new Highcharts chart configuration object. The configuration is
     * initialized with the default chart configuration (it means that all the
     * fields are not null and "ready-to-use").
     */
    public ChartConfiguration() {
        chart = new Chart();
        title = new Title();
        credits = new Credits();
        legend = new Legend();
        exporting = new Exporting();
        series = new ArrayList<>(0);
        xAxis = new Axis();
        yAxis = new Axis();
        plotOptions = new PlotOptions();
        tooltip = new Tooltip();
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
    public Axis getXaxis() {
        return xAxis;
    }

    public void setXaxis(Axis xAxis) {
        this.xAxis = xAxis;
    }

    @JsonProperty("yAxis")
    public Axis getYaxis() {
        return yAxis;
    }

    public void setYaxis(Axis yAxis) {
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

    public PlotOptions getPlotOptions() {
        return plotOptions;
    }

    public void setPlotOptions(PlotOptions plotOptions) {
        this.plotOptions = plotOptions;
    }

    public Tooltip getTooltip() {
        return tooltip;
    }

    public void setTooltip(Tooltip tooltip) {
        this.tooltip = tooltip;
    }

}
