package fr.gwombat.predicadmin.web.form;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

public class AddressForm {

    private String street1;
    private String street2;
    private String zip;
    private String city;
    private String country;
    
    public AddressForm() {
        populateDefaultCountry();
    }

    private void populateDefaultCountry() {
        country = LocaleContextHolder.getLocale().getDisplayCountry().toUpperCase();
    }

    public boolean isAllFieldsNull() {
        if (!StringUtils.isBlank(street1))
            return false;
        if (!StringUtils.isBlank(street2))
            return false;
        if (!StringUtils.isBlank(zip))
            return false;
        if (!StringUtils.isBlank(city))
            return false;
        if (!StringUtils.isBlank(country))
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
        
        if(StringUtils.isBlank(country))
            populateDefaultCountry();
    }

}
