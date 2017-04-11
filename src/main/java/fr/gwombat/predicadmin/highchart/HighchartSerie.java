package fr.gwombat.predicadmin.highchart;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gWombat.
 *
 * @since 11/04/2017
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HighchartSerie {

    private String               name;
    private List<HighchartPoint> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addData(HighchartPoint point){
        if(data == null)
            data = new ArrayList<>(0);
        data.add(point);
    }

    public List<HighchartPoint> getData() {
        return data;
    }

    public void setData(List<HighchartPoint> data) {
        this.data = data;
    }
}
