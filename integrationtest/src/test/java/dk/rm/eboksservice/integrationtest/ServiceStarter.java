package dk.rm.eboksservice.integrationtest;

import com.github.dockerjava.api.model.VolumesFrom;
import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpStatus;
import dk.rm.eboksservice.EboksServiceApplication;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Collections;

public class ServiceStarter {
    private static final Logger logger = LoggerFactory.getLogger(ServiceStarter.class);
    private static final Logger serviceLogger = LoggerFactory.getLogger("rm-eboks-service");

    private static ClientAndServer diasServerMockBuilder;
    private MockServerClient diasServerMock;
    private Network dockerNetwork;

    public void startServices() {
        dockerNetwork = Network.newNetwork();
        startDiasServer();
        String templatesPath = this.getClass().getResource("/templates.json").getPath();

        SpringApplication.run(EboksServiceApplication.class,
                "--DIAS_MAIL_SERVICE_URL=http://localhost:1080/mail/send",
                "--DIAS_MAIL_SERVICE_RECIPIENT=recipient@test.dk",
                "--DIAS_MAIL_SERVICE_SENDER=sender@test.dk",
                "--TEMPLATES_PATH=" + templatesPath);
    }

    public GenericContainer startServicesInDocker() {
        dockerNetwork = Network.newNetwork();

        var resourcesContainerName = "rm-eboks-service-resources";
        var resourcesRunning = containerRunning(resourcesContainerName);
        logger.info("Resource container is running: " + resourcesRunning);

        GenericContainer service;

        // Start service
        if (resourcesRunning) {
            VolumesFrom volumesFrom = new VolumesFrom(resourcesContainerName);
            service = new GenericContainer<>("local/rm-eboks-service-qa:dev")
                    .withCreateContainerCmdModifier(modifier -> modifier.withVolumesFrom(volumesFrom))
                    .withEnv("JVM_OPTS", "-javaagent:/jacoco/jacocoagent.jar=output=file,destfile=/jacoco-report/jacoco-it.exec,dumponexit=true,append=true -cp integrationtest.jar");
        } else {
            service = new GenericContainer<>("local/rm-eboks-service-qa:dev")
                    .withFileSystemBind("/tmp", "/jacoco-report/")
                    .withEnv("JVM_OPTS", "-javaagent:/jacoco/jacocoagent.jar=output=file,destfile=/jacoco-report/jacoco-it.exec,dumponexit=true -cp integrationtest.jar");
        }

        service.withNetwork(dockerNetwork)
                .withNetworkAliases("rm-eboks-service")

                .withEnv("LOG_LEVEL", "INFO")

//                .withEnv("JVM_OPTS", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000")

                .withExposedPorts(8081,8080)
                .waitingFor(Wait.forHttp("/actuator").forPort(8081).forStatusCode(200));
        service.start();
        attachLogger(serviceLogger, service);

        return service;
    }

    private void startDiasServer() {
        diasServerMockBuilder = ClientAndServer.startClientAndServer(1080);
        diasServerMock = new MockServerClient("localhost", 1080);

        diasServerMock.when(HttpRequest.request()
                        .withMethod("POST")
                        .withPath("/mail/send"))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatus.SC_OK)
        );
    }

    protected void stopDiasServer() {
        diasServerMockBuilder.stop();
    }

    public MockServerClient getDiasServerMock() {
        return diasServerMock;
    }

    private boolean containerRunning(String containerName) {
        return DockerClientFactory
                .instance()
                .client()
                .listContainersCmd()
                .withNameFilter(Collections.singleton(containerName))
                .exec()
                .size() != 0;
    }

    private void attachLogger(Logger logger, GenericContainer container) {
        ServiceStarter.logger.info("Attaching logger to container: " + container.getContainerInfo().getName());
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger);
        container.followOutput(logConsumer);
    }
}
