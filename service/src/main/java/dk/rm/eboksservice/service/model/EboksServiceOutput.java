package dk.rm.eboksservice.service.model;

public class EboksServiceOutput {
    private String message;

    public EboksServiceOutput(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
