package fr.gwombat.predicadmin.highchart.transformer;

import java.util.List;

import fr.gwombat.predicadmin.highchart.ChartConfiguration;
import fr.gwombat.predicadmin.highchart.Serie;

/**
 * Created by gWombat
 *
 * @since 11/04/2017
 */
public abstract class AbstractHighchartDataTransformer<T> {

    protected abstract List<Serie> createChartSeries(T source);
    
    public abstract ChartConfiguration createChartConfiguration(T source);

}
