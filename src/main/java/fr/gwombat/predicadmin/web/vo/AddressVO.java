package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.model.Address;

public class AddressVO {

    private String street1;
    private String street2;
    private String zip;
    private String city;
    private String country;

    public AddressVO() {
    }

    public AddressVO(final Address address) {
        initFromEntity(address);
    }

    private void initFromEntity(final Address address) {
        if(address != null){
            this.city = address.getCity();
            this.country = address.getCountry();
            this.street1 = address.getStreet1();
            this.street2 = address.getStreet2();
            this.zip = address.getZip();
        }
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
