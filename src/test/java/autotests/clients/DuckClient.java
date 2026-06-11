package autotests.clients;

import autotests.BaseTest;
import autotests.EndpointConfig;
import autotests.payloads.request.DuckPropertiesRequest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckClient extends BaseTest {


    @Step("Просим уточку полететь")
    public void duckFly(TestCaseRunner runner, String duckId){
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", duckId)
        );
    }

    @Step("Спрашиваем параметры уточки")
    public void duckProperties(TestCaseRunner runner,String duckId){
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", duckId));
    }

    @Step("Просим уточку что-нибудь сказать")
    public void duckQuack(TestCaseRunner runner, String duckId, String repetitionCount, String soundCount ){
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", duckId)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
    }

    @Step("Просим уточку поплыть")
    public void duckSwim(TestCaseRunner runner, String duckId){
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", duckId));
    }

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

    @Step("Удаляем уточку")
    public void deleteDuck(TestCaseRunner runner, String duckId) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", duckId));
    }

    @Step("Обновляем уточку")
    public void updateDuck(TestCaseRunner runner, String duckId, String color, double height,
                           String material, String sound, String wingsState) {
        runner.$(http()
                .client(duckService)
                .send()
                .put("/api/duck/update")
                .queryParam("id", duckId)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
    }


}