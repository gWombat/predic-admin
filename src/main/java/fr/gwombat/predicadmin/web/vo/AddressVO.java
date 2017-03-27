package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.model.Address;

/**
 * Created by gWombat
 */
public class AddressVO {

    private String street;
    private String country;
    private String zip;
    private String city;

    AddressVO(final Address address) {
        if (address != null) {
            this.country = address.getCountry();
            this.city = address.getCity();
            this.zip = address.getZip();
            this.street = computeAddress(address);
        }
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
