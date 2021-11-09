package dk.rm.eboksservice.service;

import dk.rm.eboksservice.service.model.EboksServiceInput;
import dk.rm.eboksservice.service.model.EboksServiceOutput;

public class EboksServiceImpl implements EboksService {
    @Override
    public EboksServiceOutput eboksServiceBusinessLogic(EboksServiceInput input) {
        return new EboksServiceOutput();
    }
}
