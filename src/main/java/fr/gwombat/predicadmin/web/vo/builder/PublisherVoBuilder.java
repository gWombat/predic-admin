package fr.gwombat.predicadmin.web.vo.builder;

import fr.gwombat.predicadmin.web.vo.AddressVO;
import fr.gwombat.predicadmin.web.vo.ContactDetailVO;
import fr.gwombat.predicadmin.web.vo.PublisherVO;

public class PublisherVoBuilder {

    private String          fullName;
    private String          birthDate;
    private String          identifier;
    private String          baptismDate;

    private ContactDetailVO contactDetail;
    private AddressVO       address;

    private PublisherVoBuilder() {

    }

    public static PublisherVoBuilder begin(final ContactDetailVO contactDetail, final AddressVO address) {
        final PublisherVoBuilder builder = new PublisherVoBuilder();
        builder.address = address;
        builder.contactDetail = contactDetail;
        return builder;
    }

    public PublisherVoBuilder fullName(final String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PublisherVoBuilder birthDate(final String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public PublisherVoBuilder identifier(final String identifier) {
        this.identifier = identifier;
        return this;
    }

    public PublisherVoBuilder baptismDate(final String baptismDate) {
        this.baptismDate = baptismDate;
        return this;
    }

    public PublisherVO build() {
        return new PublisherVO(this);
    }

    public String getFullName() {
        return fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getBaptismDate() {
        return baptismDate;
    }

    public ContactDetailVO getContactDetail() {
        return contactDetail;
    }

    public AddressVO getAddress() {
        return address;
    }
}
