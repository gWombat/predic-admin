package fr.gwombat.predicadmin.web.form;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.util.Assert;

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

    public ContactDetail toEntity(ContactDetail contactDetail) {
        if (allFieldsAreNull())
            return null;

        if (contactDetail == null)
            contactDetail = new ContactDetail();

        mergeFields(contactDetail);
        return contactDetail;
    }

    private void mergeFields(ContactDetail contactDetail) {
        Assert.notNull(contactDetail, "The argument [contactDetail] must not be null");

        contactDetail.setEmail(email);
        contactDetail.setMobilePhone(mobilePhone);
        contactDetail.setPhone(phone);
    }

    private boolean allFieldsAreNull() {
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
