package fr.gwombat.predicadmin.web.transformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import fr.gwombat.predicadmin.model.entities.Address;
import fr.gwombat.predicadmin.web.form.AddressForm;
import fr.gwombat.predicadmin.web.vo.AddressVO;
import fr.gwombat.predicadmin.web.vo.builder.AddressVoBuilder;

class AddressTransformer extends AbstractEntityTransformer<Address, AddressForm, AddressVO> {

    AddressTransformer(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public Address toEntity(final AddressForm addressForm, Address address) {
        if (addressForm != null) {
            if (addressForm.isAllFieldsNull())
                address = null;
            else {
                if (address == null)
                    address = new Address();

                address.setCity(WordUtils.capitalizeFully(addressForm.getCity(), ' ', '-'));
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
            addressForm.setCountry(StringUtils.upperCase(address.getCountry()));
            addressForm.setStreet1(address.getStreet1());
            addressForm.setStreet2(address.getStreet2());
            addressForm.setZip(address.getZip());

            if (addressForm.getCountry() == null)
                addressForm.setCountry(StringUtils.upperCase(LocaleContextHolder.getLocale()
                        .getDisplayCountry()));

            return addressForm;
        }
        return null;
    }

    public AddressVO toViewObject(final AddressForm address) {
        final AddressVoBuilder builder = AddressVoBuilder.create();
        if (address != null)
            builder.city(WordUtils.capitalizeFully(address.getCity(), ' ', '-'))
                    .country(address.getCountry())
                    .street(computeAddressStreet(address.getStreet1(), address.getStreet2()))
                    .zip(address.getZip());
        return builder.build();
    }

    @Override
    public AddressVO toViewObject(final Address address) {
        final AddressVoBuilder builder = AddressVoBuilder.create();
        if (address != null)
            builder.city(address.getCity())
                    .country(address.getCountry())
                    .street(computeAddressStreet(address.getStreet1(), address.getStreet2()))
                    .zip(address.getZip());
        return builder.build();
    }

    private static String computeAddressStreet(final String street1, final String street2) {
        final StringBuilder builder = new StringBuilder();
        if (!StringUtils.isBlank(street1)) {
            builder.append(street1);
            if (!StringUtils.isBlank(street2))
                builder.append(", ");
        }
        if (street2 != null)
            builder.append(street2);
        return builder.toString();
    }

}
