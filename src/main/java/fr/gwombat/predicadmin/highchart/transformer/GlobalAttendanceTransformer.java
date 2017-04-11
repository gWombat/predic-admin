package fr.gwombat.predicadmin.highchart.transformer;

import fr.gwombat.predicadmin.highchart.HighchartPoint;
import fr.gwombat.predicadmin.highchart.HighchartSerie;
import fr.gwombat.predicadmin.model.MeetingAttendance;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gWombat
 * @since 11/04/2017.
 */
@Component
public class GlobalAttendanceTransformer extends AbstractHighchartDataTransformer<List<MeetingAttendance>> {

    @Override
    public List<HighchartSerie> convertToSeries(List<MeetingAttendance> attendances) {
        if(attendances != null) {
            final List<HighchartSerie> series = new ArrayList<>(0);

            final HighchartSerie serie = new HighchartSerie("Assistance aux réunions");
            for(MeetingAttendance attendance : attendances){
                final HighchartPoint point = new HighchartPoint();
                point.setY(attendance.getAttendance());
                point.setX(attendance.getDate().atStartOfDay().atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli());
                serie.addData(point);
            }
            series.add(serie);

            return series;
        }
        return null;
    }
}
