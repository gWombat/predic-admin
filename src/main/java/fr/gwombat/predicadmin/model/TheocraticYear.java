package fr.gwombat.predicadmin.model;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "theocratic_years")
public class TheocraticYear extends AuditableEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "year_id")
    private Long              id;

    @Min(1950)
    @Column(name = "year")
    private int               year;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "congregation_id", nullable = false)
    private Congregation      congregation;

    @Transient
    private LocalDateTime     start;

    @Transient
    private LocalDateTime     end;

    private void initDatesFromYear() {
        start = LocalDateTime.of(year - 1, 9, 1, 0, 0, 0);
        end = LocalDateTime.of(year, 8, 15, 23, 59, 59).with(TemporalAdjusters.lastDayOfMonth());
    }

    @Override
    public String toString() {
        return String.format("%s-%s", year - 1, year);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        initDatesFromYear();
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

}
