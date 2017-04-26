package fr.gwombat.predicadmin.model.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import fr.gwombat.predicadmin.support.Gender;

@Entity
@Table(name = "congregations_publishers")
public class Publisher extends AuditableEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "publisher_id")
    private Long              id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String            name;

    @NotBlank
    @Column(name = "firstname", nullable = false)
    private String            firstName;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate         birthDate;

    @Column(name = "baptism_date")
    private LocalDate         baptismDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender            gender;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "congregation_id", nullable = false)
    private Congregation      congregation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address           address;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_contact_details_id")
    private ContactDetail     contactDetail;

    @Override
    public String toString() {
        return name + " " + firstName + "[" + identifier + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return firstName + " " + name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getBaptismDate() {
        return baptismDate;
    }

    public void setBaptismDate(LocalDate baptismDate) {
        this.baptismDate = baptismDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Congregation getCongregation() {
        return congregation;
    }

    public void setCongregation(Congregation congregation) {
        this.congregation = congregation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }

}
