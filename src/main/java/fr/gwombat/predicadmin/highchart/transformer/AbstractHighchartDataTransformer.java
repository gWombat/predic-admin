package fr.gwombat.predicadmin.highchart.transformer;

import fr.gwombat.predicadmin.highchart.Serie;

import java.util.List;

/**
 * Created by gWombat
 *
 * @since 11/04/2017
 */
public abstract class AbstractHighchartDataTransformer<T> {

    public abstract List<Serie> convertToSeries(T source);

}
