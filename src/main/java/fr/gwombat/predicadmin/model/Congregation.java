package fr.gwombat.predicadmin.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by gWombat.
 *
 * @since 14/03/2017
 */
@Entity
@Table(name = "congregations")
public class Congregation extends AuditableEntity {

    private static final long           serialVersionUID = -6757555507970624970L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "congregation_id")
    private Long                        id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String                      name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "congregation")
    private List<MeetingAttendance>     attendances;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "congregation")
    private List<CongregationPublisher> publishers;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "congregation")
    private List<TheocraticYear>        years;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MeetingAttendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<MeetingAttendance> attendances) {
        this.attendances = attendances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CongregationPublisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<CongregationPublisher> publishers) {
        this.publishers = publishers;
    }

    public List<TheocraticYear> getYears() {
        return years;
    }

    public void setYears(List<TheocraticYear> years) {
        this.years = years;
    }
}
