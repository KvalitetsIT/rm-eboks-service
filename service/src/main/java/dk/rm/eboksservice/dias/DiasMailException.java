package dk.rm.eboksservice.dias;

public class DiasMailException extends Exception {
    private final int statusCode;

    public DiasMailException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public DiasMailException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
