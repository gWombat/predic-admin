package fr.gwombat.predicadmin.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "meetings_attendances",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "congregation_id",
                "date"
        })
})
public class MeetingAttendance extends AuditableEntity implements Comparable<MeetingAttendance> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "meeting_attendance_id")
    private Long              id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "congregation_id", nullable = false)
    private Congregation      congregation;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate         date;

    @Min(0)
    @Column(name = "attendance", nullable = false)
    private int               attendance;
    
    @Override
    public int compareTo(MeetingAttendance other) {
        return date.compareTo(other.date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Congregation getCongregation() {
        return congregation;
    }

    public void setCongregation(Congregation congregation) {
        this.congregation = congregation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
    

}
