package fr.gwombat.predicadmin.web.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.support.Gender;

public class PublisherForm {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private DateTimeFormatter   dateTimeFormatter;
    private String              identifier;
    private String              fullName;
    @NotBlank
    @Pattern(regexp = "^(?!-)[A-zÀ-ÿ\\s-]+(?<!-)$")
    private String              name;
    @NotBlank
    @Pattern(regexp = "^(?!-)[A-zÀ-ÿ\\s-]+(?<!-)$")
    private String              firstName;
    private String              birthDate;
    private String              baptismDate;
    @NotNull
    private Gender              gender;
    private AddressForm         address;
    private ContactDetailForm   contactDetail;

    public PublisherForm() {
        dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    }

    public PublisherForm(Publisher publisher) {
        dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        initFromEntity(publisher);
    }

    /**
     * create a new entity with all new fields properly initialized or merged
     * existing entity with new fields values
     * 
     * @param publisher
     * @return
     */
    public Publisher toEntity(Publisher publisher) {
        if(publisher == null)
            publisher = new Publisher();
        
        mergeFields(publisher);
        return publisher;
    }

    private void initFromEntity(Publisher publisher) {
        if (publisher != null) {
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

    private void mergeFields(final Publisher publisher) {
        Assert.notNull(publisher, "The argument [publisher] must not be null");

        publisher.setName(StringUtils.upperCase(name));
        publisher.setFirstName(WordUtils.capitalizeFully(firstName));
        publisher.setGender(gender);
        if(!StringUtils.isBlank(baptismDate))
            publisher.setBaptismDate(LocalDate.parse(baptismDate, dateTimeFormatter));
        if(!StringUtils.isBlank(birthDate))
            publisher.setBirthDate(LocalDate.parse(birthDate, dateTimeFormatter));
        
        publisher.setAddress(address.toEntity(publisher.getAddress()));
        publisher.setContactDetail(contactDetail.toEntity(publisher.getContactDetail()));
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
