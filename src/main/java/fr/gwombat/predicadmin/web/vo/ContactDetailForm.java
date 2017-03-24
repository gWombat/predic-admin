package fr.gwombat.predicadmin.web.vo;

import org.hibernate.validator.constraints.Email;

import fr.gwombat.predicadmin.model.ContactDetail;

public class ContactDetailForm {

    @Email
    private String email;
    private String phone;
    private String mobilePhone;

    public ContactDetailForm() {

    }

    public ContactDetailForm(final ContactDetail contactDetail) {
        if (contactDetail != null) {
            this.email = contactDetail.getEmail();
            this.phone = contactDetail.getPhone();
            this.mobilePhone = contactDetail.getMobilePhone();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

}
