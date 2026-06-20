package autotests.tests.duckActionControllerTests;

import autotests.clients.duckActionControllerClients.FlyClient;
import autotests.payloads.response.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
@Epic("Тесты duck-action-controller")
@Feature("Полет уточки")
@Story("Эндпоинт /api/duck/action/swim")
public class FlyTest extends FlyClient {
    @Test(description = "уточка с активными крыльями")
    @CitrusTest
    public  void FlyTest1(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        duckFly(runner,"${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("I am flying :)");
        validateResponse(runner, expectedResponse);
        updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}");
        validateDuckNotInDb(runner, id);
    }
    @Test(description = "уточка с неактивными крыльями")
    @CitrusTest
    public  void FlyTest2(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'FIXED');");
        duckFly(runner,"${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("I can not fly :C");
        validateResponse(runner, expectedResponse);
        updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}");
        validateDuckNotInDb(runner, id);
    }

    @Test(description = "уточка с неопределенными крыльями")
    @CitrusTest
    public  void FlyTest3(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'UNDEFINED');");
        duckFly(runner,"${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Wings are not detected :(");
        validateResponse(runner, expectedResponse);
        updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}");
        validateDuckNotInDb(runner, id);
    }

}
