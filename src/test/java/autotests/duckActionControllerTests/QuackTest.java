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

public class QuackTest extends TestNGCitrusSpringSupport {
    @Test(description = "уточка с нечетным id")
    @CitrusTest
    public void quackTest1(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner,"1", "1","1");
        validateResponse(runner, jsonPath()
                .expression("$.sound", "quack"));
    }
    @Test(description = "уточка с четным id")
    @CitrusTest
    public void quackTest2(@Optional @CitrusResource TestCaseRunner runner) {
        duckQuack(runner,"2","1","1");
        validateResponse(runner, jsonPath()
                .expression("$.sound", "moo"));
    }
    public void duckQuack(TestCaseRunner runner, String duckId, String repetitionCount, String soundCount ){
        runner.$(http()
                .client("http://localhost:2222/")
                .send()
                .get("/api/duck/action/quack")
                .queryParam("id", duckId)
                .queryParam("repetitionCount", repetitionCount)
                .queryParam("soundCount", soundCount));
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
