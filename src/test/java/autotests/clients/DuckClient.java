package autotests.clients;

import autotests.EndpointConfig;
import autotests.payloads.request.DuckPropertiesRequest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    public void createDuck(TestCaseRunner runner, DuckPropertiesRequest duckPropertiesRequest) {
        runner.$(http()
                .client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(duckPropertiesRequest, new ObjectMapper()))
                );
    }


    public void validateResponse(TestCaseRunner runner, HttpStatus status,
                                 JsonPathMessageValidationContext.Builder body) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .validate(body));
    }

    public void validateResponse(TestCaseRunner runner,
                                 JsonPathMessageValidationContext.Builder body) {
        validateResponse(runner, HttpStatus.OK, body);
    }

    public void validateResponse(TestCaseRunner runner, HttpStatus status, Object expectedPayload) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(status)
                .message()
                .type(MessageType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(
                        expectedPayload,
                        new ObjectMapper()
                )));
    }

    public void validateResponse(TestCaseRunner runner, Object expectedPayload) {
        validateResponse(runner, HttpStatus.OK, expectedPayload);
    }

    public void validateResponse(TestCaseRunner runner, String expectedPayload) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .type(MessageType.JSON)
                        .extract(fromBody().expression("$.id", "duckId"))
                        .body(new ClassPathResource(expectedPayload))
        );
    }

    public void getDuckId(TestCaseRunner runner) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId")));
    }

}