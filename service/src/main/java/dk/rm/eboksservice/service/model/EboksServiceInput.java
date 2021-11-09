package dk.rm.eboksservice.service.model;

public class EboksServiceInput {
    private String cpr;
    private String template;

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
