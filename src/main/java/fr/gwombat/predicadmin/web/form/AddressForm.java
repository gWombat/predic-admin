package fr.gwombat.predicadmin.web.form;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

import fr.gwombat.predicadmin.model.Address;

public class AddressForm {

    private String street1;
    private String street2;
    private String zip;
    private String city;
    private String country;

    public AddressForm() {
    }

    public AddressForm(final Address address) {
        initFromEntity(address);
    }

    private void initFromEntity(final Address address) {
        if (address != null) {
            this.city = address.getCity();
            this.country = address.getCountry();
            if (country == null)
                resolveCountryFromContext();
            this.street1 = address.getStreet1();
            this.street2 = address.getStreet2();
            this.zip = address.getZip();
        }
    }

    public Address toEntity(Address address) {
        if (allFieldsAreNull())
            return null;

        if (address == null)
            address = new Address();

        mergeFields(address);
        return address;
    }

    private void mergeFields(final Address address) {
        Assert.notNull(address, "The argument [address] must not be null");

        address.setCity(WordUtils.capitalizeFully(city));
        address.setCountry(StringUtils.upperCase(country));
        address.setStreet1(street1);
        address.setStreet2(street2);
        address.setZip(zip);
    }

    private void resolveCountryFromContext() {
        this.country = StringUtils.upperCase(LocaleContextHolder.getLocale().getDisplayCountry());
    }

    private boolean allFieldsAreNull() {
        if (StringUtils.isBlank(street1))
            return false;
        if (StringUtils.isBlank(street2))
            return false;
        if (StringUtils.isBlank(zip))
            return false;
        if (StringUtils.isBlank(city))
            return false;
        if (StringUtils.isBlank(country))
            return false;
        return true;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
