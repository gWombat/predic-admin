package fr.gwombat.predicadmin.web.transformer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import fr.gwombat.predicadmin.model.entities.ContactDetail;
import fr.gwombat.predicadmin.web.form.ContactDetailForm;
import fr.gwombat.predicadmin.web.vo.ContactDetailVO;
import fr.gwombat.predicadmin.web.vo.builder.ContactDetailVoBuilder;

import org.springframework.context.MessageSource;

class ContactDetailTransformer extends AbstractEntityTransformer<ContactDetail, ContactDetailForm, ContactDetailVO> {

    private static final Logger logger = LoggerFactory.getLogger(ContactDetailTransformer.class);

    ContactDetailTransformer(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public ContactDetail toEntity(final ContactDetailForm contactDetailForm, ContactDetail contactDetail) {
        if (contactDetailForm != null) {
            if (contactDetailForm.isAllFieldsNull())
                contactDetail = null;
            else {
                if (contactDetail == null)
                    contactDetail = new ContactDetail();

                contactDetail.setEmail(StringUtils.lowerCase(contactDetailForm.getEmail()));
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
        ContactDetailVoBuilder builder = ContactDetailVoBuilder.create();
        if (contactDetail != null)
            builder = ContactDetailVoBuilder.create()
                    .email(contactDetail.getEmail())
                    .mobilePhone(formatPhoneNumber(contactDetail.getMobilePhone()))
                    .phone(formatPhoneNumber(contactDetail.getPhone()));
        return builder.build();
    }

    private static String formatPhoneNumber(final String strNumber) {
        if (!StringUtils.isBlank(strNumber)) {
            try {
                PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
                final Phonenumber.PhoneNumber mobilePhoneNumber = phoneNumberUtil.parse(strNumber, "FR");
                if(phoneNumberUtil.isValidNumber(mobilePhoneNumber))
                    return phoneNumberUtil.format(mobilePhoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                return "N/A";
            } catch (NumberParseException e) {
                logger.warn(e.getMessage());
                return "N/A";
            }
        }
        return "N/A";
    }

}
