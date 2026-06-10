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

import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class PropertiesTest extends TestNGCitrusSpringSupport {
    @Test(description = "Проверка Properties с четным id")
    @CitrusTest
    public void propertiesTest1(@Optional @CitrusResource TestCaseRunner runner){
        duckProperties(runner,"2");
        validateResponse( runner, jsonPath()
                .expression("$", "{}"));
    }
    @Test(description = "Проверка Properties с нечетным id")
    @CitrusTest
    public void propertiesTest2(@Optional @CitrusResource TestCaseRunner runner){
        duckProperties(runner,"1");
        validateResponse( runner, jsonPath()
                .expression("$.color", "yellow")
                .expression("$.height", "@isNumber()@")
                .expression("$.material", "rubber")
                .expression("$.sound", "quack")
                .expression("$.wingsState", "ACTIVE")
        );
    }
    public void duckProperties(TestCaseRunner runner,String duckId){
        runner.$(http()
                .client("http://localhost:2222/")
                .send()
                .get("/api/duck/action/properties")
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

