package autotests.clients.duckControllerClients;

import autotests.clients.DuckClient;
import autotests.payloads.request.DuckPropertiesRequest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class CreateClient extends DuckClient {
    @Step("Создаем уточку")
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

    @Step("Проверяем создание уточки")
    public void validateCreateResponse(TestCaseRunner runner,
                                       JsonPathMessageValidationContext.Builder body) {
        runner.$(http()
                .client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .validate(body));
    }

}
