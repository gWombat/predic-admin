package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.web.vo.builder.ContactDetailVoBuilder;

/**
 * Created by gWombat
 */
public class ContactDetailVO {

    private final String phone;
    private final String mobilePhone;
    private final String email;

    public ContactDetailVO(final ContactDetailVoBuilder builder) {
        this.phone = builder.getPhone();
        this.mobilePhone = builder.getMobilePhone();
        this.email = builder.getEmail();
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
