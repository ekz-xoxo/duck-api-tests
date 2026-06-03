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

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class SwimTests extends TestNGCitrusSpringSupport {
    @Test(description = "уточка с активными крыльями")
    @CitrusTest
    public  void swim1(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner,"2");
        validateResponse( runner, jsonPath()
                .expression("$.message", "I’m swimming"));
    }
    @Test(description = "уточка с неcуществующим id")
    @CitrusTest
    public  void swim2(@Optional @CitrusResource TestCaseRunner runner) {
        duckFly(runner,"3");
        validateBadResponse( runner, jsonPath()
                .expression("$.message", "@notEmpty()@"));
    }
    public void duckFly(TestCaseRunner runner, String duckId){
        runner.$(http()
                .client("http://localhost:2222/")
                .send()
                .get("/api/duck/action/swim")
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

    public void validateBadResponse(TestCaseRunner runner, JsonPathMessageValidationContext.Builder body){
        runner.$(http()
                .client("http://localhost:2222/")
                .receive()
                .response(HttpStatus.BAD_REQUEST)
                .message()
                .type(MessageType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .validate(body));
    }
}
