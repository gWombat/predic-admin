package fr.gwombat.predicadmin.web.transformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import fr.gwombat.predicadmin.model.Address;
import fr.gwombat.predicadmin.web.form.AddressForm;
import fr.gwombat.predicadmin.web.vo.AddressVO;
import fr.gwombat.predicadmin.web.vo.builder.AddressVoBuilder;

class AddressTransformer extends AbstractEntityTransformer<Address, AddressForm, AddressVO> {

    @Override
    public Address toEntity(final AddressForm addressForm, Address address) {
        if (addressForm != null) {
            if (addressForm.isAllFieldsNull())
                address = null;
            else {
                if (address == null)
                    address = new Address();

                address.setCity(WordUtils.capitalizeFully(addressForm.getCity()));
                address.setCountry(StringUtils.upperCase(addressForm.getCountry()));
                address.setStreet1(addressForm.getStreet1());
                address.setStreet2(addressForm.getStreet2());
                address.setZip(addressForm.getZip());
            }
        }

        return address;
    }

    @Override
    public AddressForm toFormObject(final Address address) {
        if (address != null) {
            final AddressForm addressForm = new AddressForm();
            addressForm.setCity(address.getCity());
            addressForm.setCountry(address.getCountry());
            addressForm.setStreet1(address.getStreet1());
            addressForm.setStreet2(address.getStreet2());
            addressForm.setZip(address.getZip());

            if (addressForm.getCountry() == null)
                addressForm.setCountry(StringUtils.upperCase(LocaleContextHolder.getLocale().getDisplayCountry()));

            return addressForm;
        }
        return null;
    }

    @Override
    public AddressVO toViewObject(final Address address) {
        if (address != null) {
            final AddressVoBuilder builder = AddressVoBuilder.begin().city(address.getCity()).country(address.getCountry()).street(computeAddress(address)).zip(address.getZip());

            return builder.build();
        }
        return null;
    }

    private static String computeAddress(final Address address) {
        final StringBuilder builder = new StringBuilder();
        if (address.getStreet1() != null) {
            builder.append(address.getStreet1());
            if (address.getStreet2() != null)
                builder.append(", ");
        }
        if (address.getStreet2() != null)
            builder.append(address.getStreet2());
        return builder.toString();
    }

}
