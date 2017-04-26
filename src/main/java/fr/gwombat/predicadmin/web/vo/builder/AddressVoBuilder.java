package fr.gwombat.predicadmin.web.vo.builder;

import fr.gwombat.predicadmin.web.vo.AddressVO;

public class AddressVoBuilder {

    private String street;
    private String country;
    private String zip;
    private String city;

    private AddressVoBuilder() {
    }

    public static AddressVoBuilder create() {
        return new AddressVoBuilder();
    }

    public AddressVoBuilder street(final String street) {
        this.street = street;
        return this;
    }

    public AddressVoBuilder country(final String country) {
        this.country = country;
        return this;
    }

    public AddressVoBuilder zip(final String zip) {
        this.zip = zip;
        return this;
    }

    public AddressVoBuilder city(final String city) {
        this.city = city;
        return this;
    }

    public AddressVO build() {
        return new AddressVO(this);
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
