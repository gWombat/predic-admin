package fr.gwombat.predicadmin.web.form;

import java.time.format.DateTimeFormatter;

import org.springframework.util.Assert;

import fr.gwombat.predicadmin.model.Publisher;

public class PublisherVO {

    private DateTimeFormatter dateTimeFormatter;

    private final String      fullName;
    private final String      birthDate;

    public PublisherVO(final Publisher publisher) {
        Assert.notNull(publisher, "");

        dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.fullName = publisher.getFullName();
        this.birthDate = dateTimeFormatter.format(publisher.getBirthDate());
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getFullName() {
        return fullName;
    }

}
