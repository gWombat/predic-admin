package fr.gwombat.predicadmin.web.form;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

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

    private void resolveCountryFromContext() {
        this.country = StringUtils.upperCase(LocaleContextHolder.getLocale().getDisplayCountry());
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
