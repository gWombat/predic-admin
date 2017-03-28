package fr.gwombat.predicadmin.web.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import fr.gwombat.predicadmin.model.ContactDetail;
import fr.gwombat.predicadmin.web.form.ContactDetailForm;
import fr.gwombat.predicadmin.web.vo.ContactDetailVO;
import fr.gwombat.predicadmin.web.vo.builder.ContactDetailVoBuilder;

public class ContactDetailTransformer extends AbstractEntityTransformer<ContactDetail, ContactDetailForm, ContactDetailVO> {

    private static final Logger logger = LoggerFactory.getLogger(ContactDetailTransformer.class);

    @Override
    public ContactDetail toEntity(final ContactDetailForm contactDetailForm, ContactDetail contactDetail) {
        if (contactDetailForm != null) {
            if (contactDetailForm.isAllFieldsNull())
                contactDetail = null;
            else {
                if (contactDetail == null)
                    contactDetail = new ContactDetail();

                contactDetail.setEmail(contactDetailForm.getEmail());
                contactDetail.setMobilePhone(contactDetailForm.getMobilePhone());
                contactDetail.setPhone(contactDetailForm.getPhone());
            }
        }
        return contactDetail;
    }

    @Override
    public ContactDetailForm toFormObject(final ContactDetail contactDetail) {
        if (contactDetail != null) {
            final ContactDetailForm contactDetailForm = new ContactDetailForm();
            contactDetailForm.setEmail(contactDetail.getEmail());
            contactDetailForm.setPhone(contactDetail.getPhone());
            contactDetailForm.setMobilePhone(contactDetail.getMobilePhone());
            return contactDetailForm;
        }
        return null;
    }

    @Override
    public ContactDetailVO toViewObject(final ContactDetail contactDetail) {
        if (contactDetail != null) {
            ContactDetailVoBuilder builder = ContactDetailVoBuilder.begin()
                    .email(contactDetail.getEmail())
                    .mobilePhone(formatPhoneNumber(contactDetail.getMobilePhone()))
                    .phone(formatPhoneNumber(contactDetail.getPhone()));

            return builder.build();
        }
        return null;
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

}
