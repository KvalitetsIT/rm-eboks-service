package dk.rm.eboksservice.service;

public class EboksServiceException extends Exception {
    public EboksServiceException(String message) {
        super(message);
    }

    public EboksServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
