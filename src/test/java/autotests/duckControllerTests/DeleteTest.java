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
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DeleteTest extends TestNGCitrusSpringSupport {
    @Test(description = "удаление утки")
    @CitrusTest
    public void deleteDuckTest(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");

        validateResponse(runner, jsonPath()
                .expression("$.message", "Duck is deleted")
        );
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

    public void deleteDuck(TestCaseRunner runner, String duckId) {
        runner.$(http()
                .client("http://localhost:2222/")
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", duckId));
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
