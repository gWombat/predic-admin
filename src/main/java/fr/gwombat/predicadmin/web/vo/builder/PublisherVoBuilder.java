package fr.gwombat.predicadmin.web.vo.builder;

import java.time.LocalDate;

import fr.gwombat.predicadmin.web.vo.AddressVO;
import fr.gwombat.predicadmin.web.vo.ContactDetailVO;
import fr.gwombat.predicadmin.web.vo.PrivilegeVO;
import fr.gwombat.predicadmin.web.vo.PublisherVO;

public class PublisherVoBuilder {

    private String          fullName;
    private String          identifier;
    private String          name;
    private String          firstName;

    private LocalDate       birthDate;
    private LocalDate       baptismDate;

    private ContactDetailVO contactDetail;
    private AddressVO       address;
    private PrivilegeVO     privilege;

    public static PublisherVoBuilder create(final ContactDetailVO contactDetail, final AddressVO address) {
        final PublisherVoBuilder builder = new PublisherVoBuilder();
        builder.address = address;
        builder.contactDetail = contactDetail;
        return builder;
    }

    public PublisherVoBuilder fullName(final String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PublisherVoBuilder birthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public PublisherVoBuilder identifier(final String identifier) {
        this.identifier = identifier;
        return this;
    }

    public PublisherVoBuilder baptismDate(final LocalDate baptismDate) {
        this.baptismDate = baptismDate;
        return this;
    }

    public PublisherVoBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public PublisherVoBuilder firstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }
    
    public PublisherVoBuilder privilege(final PrivilegeVO privilege) {
        this.privilege = privilege;
        return this;
    }

    public PublisherVO build() {
        return new PublisherVO(this);
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getIdentifier() {
        return identifier;
    }

    public LocalDate getBaptismDate() {
        return baptismDate;
    }

    public ContactDetailVO getContactDetail() {
        return contactDetail;
    }

    public AddressVO getAddress() {
        return address;
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
}
