package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.model.Publisher;
import org.springframework.util.Assert;

import java.time.format.DateTimeFormatter;

public class PublisherVO {

    private final String fullName;
    private final String birthDate;
    private final String identifier;

    private final ContactDetailVO contactDetail;

    public PublisherVO(final Publisher publisher) {
        Assert.notNull(publisher, "");
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.fullName = publisher.getFullName();
        this.birthDate = dateTimeFormatter.format(publisher.getBirthDate());
        this.identifier = publisher.getIdentifier();
        this.contactDetail = new ContactDetailVO(publisher.getContactDetail());
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
}
