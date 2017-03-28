package fr.gwombat.predicadmin.web.vo.builder;

import fr.gwombat.predicadmin.web.vo.ContactDetailVO;

public class ContactDetailVoBuilder {

    private String phone;
    private String mobilePhone;
    private String email;

    private ContactDetailVoBuilder() {

    }

    public static ContactDetailVoBuilder begin() {
        return new ContactDetailVoBuilder();
    }

    public ContactDetailVoBuilder phone(final String phone) {
        this.phone = phone;
        return this;
    }

    public ContactDetailVoBuilder mobilePhone(final String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public ContactDetailVoBuilder email(final String email) {
        this.email = email;
        return this;
    }

    public ContactDetailVO build() {
        return new ContactDetailVO(this);
    }

    public String getPhone() {
        return phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getEmail() {
        return email;
    }

}
