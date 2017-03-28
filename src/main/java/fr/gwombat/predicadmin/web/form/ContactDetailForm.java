package fr.gwombat.predicadmin.web.form;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;

public class ContactDetailForm {

    @Email
    private String email;
    private String phone;
    private String mobilePhone;

    public boolean isAllFieldsNull() {
        if (!StringUtils.isBlank(email))
            return false;
        if (!StringUtils.isBlank(phone))
            return false;
        if (!StringUtils.isBlank(mobilePhone))
            return false;
        return true;
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
