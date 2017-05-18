package fr.gwombat.predicadmin.upload.excel;


import com.fasterxml.jackson.annotation.JsonValue;

//@JsonSerialize(using = UploadablePublisherFieldSerializer.class)
public enum UploadablePublisherFields implements UploadableField {

    NAME("general.name"),
    FIRSTNAME("general.firstname"),
    BIRTHDATE("general.birthdate"),
    BAPTISMDATE("general.baptism.date")

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
