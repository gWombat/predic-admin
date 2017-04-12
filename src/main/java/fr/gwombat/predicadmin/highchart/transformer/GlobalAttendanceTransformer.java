package fr.gwombat.predicadmin.highchart.transformer;

import fr.gwombat.predicadmin.highchart.Point;
import fr.gwombat.predicadmin.highchart.Serie;
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
    public List<Serie> convertToSeries(List<MeetingAttendance> attendances) {
        if(attendances != null) {
            final List<Serie> series = new ArrayList<>(0);

            final Serie serie = new Serie("Assistance aux réunions");
            for(MeetingAttendance attendance : attendances){
                final Point point = new Point();
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
