package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.web.vo.builder.PublisherVoBuilder;

public class PublisherVO {

    private final String          fullName;
    private final String          birthDate;
    private final String          identifier;
    private final String          baptismDate;

    private final ContactDetailVO contactDetail;
    private final AddressVO       address;

    public PublisherVO(final PublisherVoBuilder builder) {
        this.fullName = builder.getFullName();
        this.birthDate = builder.getBirthDate();
        this.baptismDate = builder.getBaptismDate();
        this.identifier = builder.getIdentifier();
        this.contactDetail = builder.getContactDetail();
        this.address = builder.getAddress();
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
