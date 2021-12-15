package dk.rm.eboksservice.service;

import dk.rm.eboksservice.dias.DiasMailClient;
import dk.rm.eboksservice.dias.DiasMailException;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import dk.rm.eboksservice.service.model.EboksServiceOutput;
import dk.rm.eboksservice.templates.Template;

import java.util.Collection;
import java.util.Map;

public class EboksServiceImpl implements EboksService {
    private final DiasMailClient diasMailClient;
    private final Map<String, Template> templates;

    public EboksServiceImpl(DiasMailClient diasMailClient, Map<String, Template> templates) {
        this.diasMailClient = diasMailClient;
        this.templates = templates;
    }

    @Override
    public EboksServiceOutput sendToEboks(EboksServiceInput input) throws DiasMailException, EboksServiceException {
        if (!templates.containsKey(input.getTemplate())) {
            throw new EboksServiceException("Invalid template specified: " + input.getTemplate());
        }
        Template template = templates.get(input.getTemplate());
        diasMailClient.sendMail(input.getCpr(), template.getTemplateBody());
        return new EboksServiceOutput("success");
    }

    @Override
    public Collection<Template> getTemplates() {
        return templates.values();
    }
}
