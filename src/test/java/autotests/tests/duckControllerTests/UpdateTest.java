package autotests.tests.duckControllerTests;

import autotests.payloads.request.DuckPropertiesRequest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckControllerClients.UpdateClient;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class UpdateTest extends UpdateClient {
    @Test(description = "обновление цвета и высоты утки")
    @CitrusTest
    public void updateTest1(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        runner.$(doFinally().actions(context->
                updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}")));
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        validateDuckInDb(runner,id,"yellow","0.01","rubber", "quack", "ACTIVE");
        updateDuck(runner, "${duckId}","pink", 0.05, "rubber", "quack", "ACTIVE");
        validateDuckInDb(runner,id,"pink","0.05","rubber", "quack", "ACTIVE");
        validateResponse(runner, jsonPath()
                .expression("$.message", "Duck with id = " + id + " is updated"));
    }
    @Test(description = "обновление звука и цвета утки")
    @CitrusTest
    public void updateTest2(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        runner.$(doFinally().actions(context->
                updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}")));
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        validateDuckInDb(runner,id,"yellow","0.01","rubber", "quack", "ACTIVE");
        updateDuck(runner, "${duckId}","red", 0.01, "rubber", "quack-quack", "ACTIVE");
        validateDuckInDb(runner,id,"red","0.01","rubber", "quack-quack", "ACTIVE");
        validateResponse(runner, jsonPath()
                .expression("$.message", "Duck with id = " + id + " is updated"));

    }

}
