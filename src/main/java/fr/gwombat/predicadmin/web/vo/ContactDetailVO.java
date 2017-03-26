package fr.gwombat.predicadmin.web.vo;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import fr.gwombat.predicadmin.model.ContactDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Created by gWombat
 */
public class ContactDetailVO {

    private static final Logger logger = LoggerFactory.getLogger(ContactDetailVO.class);

    private final String phone;
    private final String mobilePhone;
    private final String email;

    ContactDetailVO(final ContactDetail contactDetail) {
        Assert.notNull(contactDetail, "");

        this.email = contactDetail.getEmail();
        this.phone = formatPhoneNumber(contactDetail.getPhone());
        this.mobilePhone = formatPhoneNumber(contactDetail.getMobilePhone());
    }

    private static String formatPhoneNumber(final String strNumber) {
        if (strNumber != null) {
            try {
                PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
                final Phonenumber.PhoneNumber mobilePhoneNumber = phoneNumberUtil.parse(strNumber, "FR");
                return phoneNumberUtil.format(mobilePhoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            } catch (NumberParseException e) {
                logger.warn(e.getMessage());
                return "N/A";
            }
        }
        return "N/A";
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
