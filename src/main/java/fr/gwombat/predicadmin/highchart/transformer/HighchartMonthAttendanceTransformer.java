package fr.gwombat.predicadmin.highchart.transformer;

import fr.gwombat.predicadmin.highchart.Point;
import fr.gwombat.predicadmin.highchart.Serie;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by gWombat.
 *
 * @since 11/04/2017
 */
@Component
public class HighchartMonthAttendanceTransformer extends AbstractHighchartDataTransformer<MonthAttendanceVO> {

    public List<Serie> convertToSeries(final MonthAttendanceVO monthAttendanceVo) {
        final List<Serie> series = new ArrayList<>(0);

        final Serie serie1 = new Serie();
        serie1.setName("Attendance");

        final int year = monthAttendanceVo.getPeriod().getYear();
        final int month = monthAttendanceVo.getPeriod().getMonth();
        final int nbDaysInMonth = monthAttendanceVo.getPeriod().getEnd().getDayOfMonth();
        final Map<LocalDate, Point> monthPoints = new HashMap<>(nbDaysInMonth);
        for (int i = 0; i < nbDaysInMonth; i++) {
            final LocalDate date = LocalDate.of(year, month, i + 1);
            final Point point = new Point();
            point.setX(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            monthPoints.put(date, point);
        }

        for (MeetingAttendanceVO meeting : monthAttendanceVo.getAttendances()) {
            final Point point = new Point();
            point.setX(meeting.getDate().atTime(12, 0).atZone(ZoneId.of("Europe/Paris")).toInstant().toEpochMilli());
            point.setY(meeting.getAttendance());

            monthPoints.put(meeting.getDate(), point);
        }
        final List<Point> serie1Points = monthPoints.entrySet().stream()
                .map(x -> x.getValue())
                .collect(Collectors.toList());
        serie1.setData(serie1Points);
        series.add(serie1);

        return series;
    }
}
