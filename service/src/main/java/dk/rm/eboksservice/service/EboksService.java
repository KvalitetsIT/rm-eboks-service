package dk.rm.eboksservice.service;

import dk.rm.eboksservice.dias.DiasMailException;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import dk.rm.eboksservice.service.model.EboksServiceOutput;
import dk.rm.eboksservice.templates.Template;

import java.util.Collection;

public interface EboksService {
    EboksServiceOutput sendToEboks(EboksServiceInput input) throws DiasMailException, EboksServiceException;
    Collection<Template> getTemplates();
}
