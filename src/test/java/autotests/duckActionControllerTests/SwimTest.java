package autotests.duckActionControllerTests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;


public class SwimTest extends TestNGCitrusSpringSupport {
    @Test(description = "уточка с существующим id")
    @CitrusTest
    public void swimTest1(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        getDuckId(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, jsonPath()
                .expression("$.message", "Paws are not found (((("));
    }

    @Test(description = "уточка с неcуществующим id")
    @CitrusTest
    public void swimTest2(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner, "999");
        validateResponse(runner, jsonPath()
                .expression("$.message", "Paws are not found (((("));
    }


    public void duckSwim(TestCaseRunner runner, String duckId) {
        runner.$(http()
                .client("http://localhost:2222/")
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", duckId));
    }

    public void validateResponse(TestCaseRunner runner, JsonPathMessageValidationContext.Builder body) {
        runner.$(http()
                .client("http://localhost:2222/")
                .receive()
                .response(HttpStatus.NOT_FOUND)
                .message()
                .type(MessageType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .validate(body));
    }

    public void createDuck(TestCaseRunner runner, String color, double height,
                           String material, String sound, String wingsState) {
        runner.$(http()
                .client("http://localhost:2222/")
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "  \"color\": \"" + color + "\",\n" +
                        "  \"height\": " + height + ",\n" +
                        "  \"material\": \"" + material + "\",\n" +
                        "  \"sound\": \"" + sound + "\",\n" +
                        "  \"wingsState\": \"" + wingsState + "\"\n" +
                        "}"));
    }

    public void getDuckId(TestCaseRunner runner) {
        runner.$(http()
                .client("http://localhost:2222/")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .extract(fromBody().expression("$.id", "duckId")));
    }

}