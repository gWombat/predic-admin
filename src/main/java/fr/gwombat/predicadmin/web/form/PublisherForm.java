package fr.gwombat.predicadmin.web.form;

import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.support.Gender;

public class PublisherForm {

    private DateTimeFormatter dateTimeFormatter;
    private String            identifier;
    private String            fullName;
    @NotBlank
    private String            name;
    @NotBlank
    private String            firstName;
    @NotBlank
    private String            birthDate;
    @NotBlank
    private String            baptismDate;
    @NotNull
    private Gender            gender;
    private AddressForm       address;
    private ContactDetailForm contactDetail;

    public PublisherForm() {

    }

    public PublisherForm(Publisher publisher) {
        initFromEntity(publisher);
    }

    private void initFromEntity(Publisher publisher) {
        if (publisher != null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            if (publisher.getBaptismDate() != null)
                this.baptismDate = dateTimeFormatter.format(publisher.getBaptismDate());
            if (publisher.getBirthDate() != null)
                this.birthDate = dateTimeFormatter.format(publisher.getBirthDate());
            this.firstName = publisher.getFirstName();
            this.name = publisher.getName();
            this.gender = publisher.getGender();
            this.identifier = publisher.getIdentifier();
            this.fullName = publisher.getFullName();
            this.address = new AddressForm(publisher.getAddress());
            this.contactDetail = new ContactDetailForm(publisher.getContactDetail());
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBaptismDate() {
        return baptismDate;
    }

    public void setBaptismDate(String baptismDate) {
        this.baptismDate = baptismDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public AddressForm getAddress() {
        return address;
    }

    public void setAddress(AddressForm address) {
        this.address = address;
    }

    public ContactDetailForm getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetailForm contactDetail) {
        this.contactDetail = contactDetail;
    }

}
