package dk.rm.eboksservice.service;

import dk.rm.eboksservice.service.model.EboksServiceOutput;
import dk.rm.eboksservice.service.model.EboksServiceInput;

public interface EboksService {
    EboksServiceOutput eboksServiceBusinessLogic(EboksServiceInput input);
}
