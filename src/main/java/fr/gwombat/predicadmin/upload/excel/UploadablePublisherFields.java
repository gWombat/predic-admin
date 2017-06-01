package fr.gwombat.predicadmin.upload.excel;


import com.fasterxml.jackson.annotation.JsonValue;

public enum UploadablePublisherFields implements UploadableField {

    NAME("general.name"),
    FIRSTNAME("general.firstname"),
    BIRTHDATE("general.birthdate"),
    BAPTISMDATE("general.baptism.date"),
    EMAIL("general.email"),
    PHONE("general.phone"),
    MOBILEPHONE("general.mobile"),
    STREET("general.street"),
    ZIP("general.zip"),
    CITY("general.city")

    ;

    private final String label;

    private UploadablePublisherFields(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @JsonValue
    public String getName(){return this.name();}

}
