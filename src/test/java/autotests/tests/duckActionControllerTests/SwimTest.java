package autotests.tests.duckActionControllerTests;

import autotests.clients.DuckClient;
import autotests.payloads.response.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты duck-action-controller")
@Feature("Плавание уточки")
@Story("Эндпоинт /api/duck/action/swim")
public class SwimTest extends DuckClient {
    @Test(description = "уточка с существующим id")
    @CitrusTest
    public  void swimTest1(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        runner.$(doFinally().actions(context->
                updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}")));
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        duckSwim(runner,id);
        validateDuckInDb(runner,id,"yellow","0.01","rubber", "quack", "ACTIVE");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Paws are not found ((((");
        validateResponse(runner, HttpStatus.NOT_FOUND, expectedResponse);
    }

    @Test(description = "уточка с неcуществующим id")
    @CitrusTest
    public  void swimTest2(@Optional @CitrusResource TestCaseRunner runner) {
        duckSwim(runner,"999");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Paws are not found ((((");
        validateResponse(runner, HttpStatus.NOT_FOUND, expectedResponse);
    }

}
