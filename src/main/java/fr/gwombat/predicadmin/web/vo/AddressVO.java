package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.web.vo.builder.AddressVoBuilder;

/**
 * Created by gWombat
 */
public class AddressVO {

    private final String street;
    private final String country;
    private final String zip;
    private final String city;

    public AddressVO(AddressVoBuilder builder) {
        this.country = builder.getCountry();
        this.city = builder.getCity();
        this.zip = builder.getZip();
        this.street = builder.getStreet();
    }

    public String getStreet() {
        return street;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }
}
