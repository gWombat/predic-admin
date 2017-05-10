package fr.gwombat.predicadmin.model.entities;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import fr.gwombat.predicadmin.model.converter.PeriodConverter;
import fr.gwombat.predicadmin.support.period.Period;

@Entity
@Table(name = "publisher_reports")
public class PublisherReport extends AuditableEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "publisher_report_id")
    private Long              id;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher         publisher;

    @NotNull
    @Convert(converter = PeriodConverter.class)
    private Period            period;

    @Min(0)
    @Max(50000)
    private int               timeAsMinutes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public int getTimeAsMinutes() {
        return timeAsMinutes;
    }

    public void setTimeAsMinutes(int time) {
        this.timeAsMinutes = time;
    }

}
