package fr.gwombat.predicadmin.web.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.util.Assert;

import fr.gwombat.predicadmin.model.Publisher;

public class PublisherVO {

    private final String          fullName;
    private final String          birthDate;
    private final String          identifier;
    private final String          baptismDate;

    private final ContactDetailVO contactDetail;
    private final AddressVO       address;

    public PublisherVO(final Publisher publisher) {
        Assert.notNull(publisher, "the field [publisher] must not be null");

        this.fullName = publisher.getFullName();
        this.birthDate = formatDate(publisher.getBirthDate());
        this.baptismDate = formatDate(publisher.getBaptismDate());
        this.identifier = publisher.getIdentifier();
        this.contactDetail = new ContactDetailVO(publisher.getContactDetail());
        this.address = new AddressVO(publisher.getAddress());
    }

    private static String formatDate(final LocalDate date) {
        if (date != null) {
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            return dateTimeFormatter.format(date);
        }
        return "N/A";
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ContactDetailVO getContactDetail() {
        return contactDetail;
    }

    public AddressVO getAddress() {
        return address;
    }

    public String getBaptismDate() {
        return baptismDate;
    }
}
