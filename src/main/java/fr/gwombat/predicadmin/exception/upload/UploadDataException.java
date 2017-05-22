package fr.gwombat.predicadmin.exception.upload;

import java.util.UUID;

public class UploadDataException extends Exception {

    private static final long serialVersionUID = 1L;

    protected Object[]        messageArguments;
    private String            messageCode;
    private final String      id               = UUID.randomUUID().toString();

    public UploadDataException() {
        super();
    }

    public UploadDataException(final Throwable throwable) {
        super(throwable);
    }

    public UploadDataException(final String messageCode, final Throwable throwable) {
        super(throwable);
        this.messageCode = messageCode;
    }

    public Object[] getMessageArguments() {
        return messageArguments;
    }

    protected void setMessageArguments(Object[] messageArguments) {
        this.messageArguments = messageArguments;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getId() {
        return id;
    }

}
