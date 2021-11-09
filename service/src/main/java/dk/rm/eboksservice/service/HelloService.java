package dk.rm.eboksservice.service;

import dk.rm.eboksservice.service.model.HelloServiceOutput;
import dk.rm.eboksservice.service.model.HelloServiceInput;

public interface HelloService {
    HelloServiceOutput helloServiceBusinessLogic(HelloServiceInput input);
}
