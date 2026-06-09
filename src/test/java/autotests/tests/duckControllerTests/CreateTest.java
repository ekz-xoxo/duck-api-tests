package autotests.tests.duckControllerTests;

import autotests.clients.DuckClient;
import autotests.payloads.request.DuckPropertiesRequest;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class CreateTest extends DuckClient {
    @Test(description = "cоздание утки из rubber")
    @CitrusTest
    public void createTest1(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        runner.$(doFinally().actions(context->
                updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}")));
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        validateDuckInDb(runner,id,"yellow","0.01","rubber", "quack", "ACTIVE");


    }
    @Test(description = "создание утки из wood")
    @CitrusTest
    public void createTest2(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        runner.$(doFinally().actions(context->
                updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}")));
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'wood', 'quack', 'ACTIVE');");
        validateDuckInDb(runner,id,"yellow","0.01","wood", "quack", "ACTIVE");
    }

}
