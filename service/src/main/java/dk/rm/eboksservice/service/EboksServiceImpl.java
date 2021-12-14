package dk.rm.eboksservice.service;

import dk.rm.eboksservice.dias.DiasMailClient;
import dk.rm.eboksservice.dias.DiasMailException;
import dk.rm.eboksservice.service.model.EboksServiceInput;
import dk.rm.eboksservice.service.model.EboksServiceOutput;

public class EboksServiceImpl implements EboksService {
    private final DiasMailClient diasMailClient;

    public EboksServiceImpl(DiasMailClient diasMailClient) {
        this.diasMailClient = diasMailClient;
    }

    @Override
    public EboksServiceOutput eboksServiceBusinessLogic(EboksServiceInput input) throws DiasMailException {
        diasMailClient.sendMail(input.getCpr(), "test");
        return new EboksServiceOutput();
    }
}
