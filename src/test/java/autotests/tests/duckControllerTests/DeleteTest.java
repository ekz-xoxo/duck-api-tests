package autotests.tests.duckControllerTests;

import autotests.payloads.request.DuckPropertiesRequest;
import autotests.payloads.response.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckControllerClients.DeleteClient;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("Тесты duck-controller")
@Feature("Удаление уточки")
@Story("Эндпоинт /api/duck/delete")
public class DeleteTest extends DeleteClient {
    @Test(description = "удаление утки")
    @CitrusTest
    public void deleteDuckTest(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        deleteDuck(runner, "${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Duck is deleted");
        validateResponse(runner,expectedResponse);
        validateDuckNotInDb(runner, id);
    }

}
