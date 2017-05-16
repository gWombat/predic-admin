package fr.gwombat.predicadmin.upload.excel;


//@JsonSerialize(using = UploadablePublisherFieldSerializer.class)
public enum UploadablePublisherFields implements UploadableField {

    NAME("general.name"),
    FIRSTNAME("general.firstname"),
    BIRTHDATE("general.birthdate")

    ;

    private final String label;

    private UploadablePublisherFields(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
