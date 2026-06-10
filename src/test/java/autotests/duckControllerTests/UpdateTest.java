package autotests.duckControllerTests;

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

public class UpdateTest extends TestNGCitrusSpringSupport {
    @Test(description = "обновление цвета и высоты утки")
    @CitrusTest
    public void updateTest1(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        getDuckId(runner);
        updateDuck(runner, "${duckId}","pink", 0.05, "rubber", "quack", "ACTIVE");
        validateResponse(runner, jsonPath()
                .expression("$.message", "Duck with id = ${duckId} is updated"));

    }
    @Test(description = "обновление звука и цвета утки")
    @CitrusTest
    public void updateTest2(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        getDuckId(runner);
        updateDuck(runner, "${duckId}","red", 0.01, "rubber", "quack-quack", "ACTIVE");

        validateResponse(runner, jsonPath()
                .expression("$.message", "Duck with id = ${duckId} is updated"));

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

    public void updateDuck(TestCaseRunner runner, String duckId, String color, double height,
                           String material, String sound, String wingsState) {
        runner.$(http()
                .client("http://localhost:2222/")
                .send()
                .put("/api/duck/update")
                .queryParam("id", duckId)
                .queryParam("color", color)
                .queryParam("height", String.valueOf(height))
                .queryParam("material", material)
                .queryParam("sound", sound)
                .queryParam("wingsState", wingsState));
    }

    public void validateResponse(TestCaseRunner runner, JsonPathMessageValidationContext.Builder body){
        runner.$(http()
                .client("http://localhost:2222/")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .validate(body));
    }
}
