package dk.rm.eboksservice.service;

import dk.rm.eboksservice.service.model.HelloServiceInput;
import dk.rm.eboksservice.service.model.HelloServiceOutput;

import java.time.ZonedDateTime;

public class HelloServiceImpl implements HelloService {
    @Override
    public HelloServiceOutput helloServiceBusinessLogic(HelloServiceInput input) {
        var output = new HelloServiceOutput();
        output.setNow(ZonedDateTime.now());
        output.setName(input.getName());

        return output;
    }
}
