package fr.gwombat.predicadmin.web.vo;

import java.time.LocalDate;

import fr.gwombat.predicadmin.web.vo.builder.PublisherVoBuilder;

public class PublisherVO {

    private final String          name;
    private final String          firstName;
    private final String          fullName;
    private final String          identifier;

    private boolean               newPublisher;

    private final LocalDate       birthDate;
    private final LocalDate       baptismDate;

    private final ContactDetailVO contactDetail;
    private final AddressVO       address;
    private final PrivilegeVO     privilege;

    public PublisherVO(final PublisherVoBuilder builder) {
        this.fullName = builder.getFullName();
        this.birthDate = builder.getBirthDate();
        this.baptismDate = builder.getBaptismDate();
        this.identifier = builder.getIdentifier();
        this.contactDetail = builder.getContactDetail();
        this.address = builder.getAddress();
        this.name = builder.getName();
        this.firstName = builder.getFirstName();
        this.privilege = builder.getPrivilege();
    }

    public LocalDate getBirthDate() {
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

    public LocalDate getBaptismDate() {
        return baptismDate;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public PrivilegeVO getPrivilege() {
        return privilege;
    }

    public boolean isNewPublisher() {
        return newPublisher;
    }

    public void setNewPublisher(boolean newPublisher) {
        this.newPublisher = newPublisher;
    }
}
