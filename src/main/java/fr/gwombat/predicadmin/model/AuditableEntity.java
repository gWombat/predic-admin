package fr.gwombat.predicadmin.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
class AuditableEntity extends BasicEntity {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by")
    private String            createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String            lastModifiedBy;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDate         createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDate         lastModifiedDate;

    protected AuditableEntity() {
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
