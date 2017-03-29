package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.web.vo.builder.PublisherVoBuilder;

public class PublisherVO {

    private final String name;
    private final String firstName;
    private final String fullName;
    private final String birthDate;
    private final String identifier;
    private final String baptismDate;

    private final ContactDetailVO contactDetail;
    private final AddressVO       address;

    public PublisherVO(final PublisherVoBuilder builder) {
        this.fullName = builder.getFullName();
        this.birthDate = builder.getBirthDate();
        this.baptismDate = builder.getBaptismDate();
        this.identifier = builder.getIdentifier();
        this.contactDetail = builder.getContactDetail();
        this.address = builder.getAddress();
        this.name = builder.getName();
        this.firstName = builder.getFirstName();
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

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }
}
