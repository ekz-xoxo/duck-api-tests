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

public class FlyTest extends TestNGCitrusSpringSupport {

    @Test(description = "уточка с активными крыльями")
    @CitrusTest
    public  void FlyTest1(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "ACTIVE");
        validateCreateResponse(runner, jsonPath()
                .expression("$.id", "@isNumber()@")
                .expression("$.wingsState", "ACTIVE"));
        duckFly(runner,"${duckId}");
        validateResponse( runner, jsonPath()
                .expression("$.message", "I am flying :)"));
    }
    @Test(description = "уточка с неактивными крыльями")
    @CitrusTest
    public  void FlyTest2(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "FIXED");
        validateCreateResponse(runner, jsonPath()
                .expression("$.id", "@isNumber()@")
                .expression("$.wingsState", "FIXED"));
        duckFly(runner,"${duckId}");
        validateResponse( runner, jsonPath()
                .expression("$.message", "I can not fly :C"));
    }
    @Test(description = "уточка с неопределенными крыльями")
    @CitrusTest
    public  void FlyTest3(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.01, "rubber", "quack", "UNDEFINED");
        validateCreateResponse(runner, jsonPath()
                .expression("$.id", "@isNumber()@")
                .expression("$.wingsState", "UNDEFINED"));
        duckFly(runner, "${duckId}");
        validateResponse(runner, jsonPath()
                .expression("$.message", "Wings are not detected :("));
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

    public void validateCreateResponse(TestCaseRunner runner,
                                       JsonPathMessageValidationContext.Builder body) {
        runner.$(http()
                .client("http://localhost:2222/")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type(MessageType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .validate(body));
    }

    public void duckFly(TestCaseRunner runner, String duckId){
        runner.$(http()
                .client("http://localhost:2222/")
                .send()
                .get("/api/duck/action/fly")
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
