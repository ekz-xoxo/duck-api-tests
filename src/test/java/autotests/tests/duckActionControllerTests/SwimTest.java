package autotests.tests.duckActionControllerTests;

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
import autotests.clients.duckActionControllerClients.SwimClient;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты duck-action-controller")
@Feature("Плавание уточки")
@Story("Эндпоинт /api/duck/action/swim")
public class SwimTest extends SwimClient {
    @Test(description = "уточка с существующим id")
    @CitrusTest
    public  void swimTest1(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        duckSwim(runner,id);
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Paws are not found ((((");
        validateResponse(runner, HttpStatus.NOT_FOUND, expectedResponse);
        updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}");
        validateDuckNotInDb(runner, id);
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
