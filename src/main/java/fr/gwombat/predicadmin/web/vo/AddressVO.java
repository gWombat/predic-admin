package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.model.Address;
import org.springframework.util.Assert;

/**
 * Created by gWombat
 */
public class AddressVO {

    private final String street;
    private final String country;
    private final String zip;
    private final String city;

    AddressVO(final Address address) {
        Assert.notNull(address, "");

        this.country = address.getCountry();
        this.city = address.getCity();
        this.zip = address.getZip();
        this.street = computeAddress(address);
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
