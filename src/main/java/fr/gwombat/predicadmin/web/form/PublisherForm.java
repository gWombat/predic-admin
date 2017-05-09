package fr.gwombat.predicadmin.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import fr.gwombat.predicadmin.support.Gender;
import fr.gwombat.predicadmin.support.Privilege;

public class PublisherForm {

    private String            identifier;
    private String            fullName;
    @NotBlank
    @Pattern(regexp = "^(?!-)[A-zÀ-ÿ\\s-]+(?<!-)$")
    private String            name;
    @NotBlank
    @Pattern(regexp = "^(?!-)[A-zÀ-ÿ\\s-]+(?<!-)$")
    private String            firstName;
    private String            birthDate;
    private String            baptismDate;
    @NotNull
    private Gender            gender;
    private AddressForm       address;
    private ContactDetailForm contactDetail;
    private Privilege         privilege;

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

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

}
