package autotests.tests.duckControllerTests;

import autotests.payloads.request.DuckPropertiesRequest;
import autotests.payloads.response.MessageResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import autotests.clients.duckControllerClients.DeleteClient;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

public class DeleteTest extends DeleteClient {
    @Test(description = "удаление утки")
    @CitrusTest
    public void deleteDuckTest(@Optional @CitrusResource TestCaseRunner runner) {
        String id = runner.variable("duckId","123");
        runner.$(doFinally().actions(context->
                updateDatabase(runner,"DELETE FROM DUCK WHERE ID =${duckId}")));
        updateDatabase(runner,
                "insert into DUCK (id,color,height,material,sound,wings_state)\n" +
                        "values(" + id + ",'yellow',0.01, 'rubber', 'quack', 'ACTIVE');");
        validateDuckInDb(runner,id,"yellow","0.01","rubber", "quack", "ACTIVE");
        deleteDuck(runner, "${duckId}");
        MessageResponse expectedResponse = new MessageResponse();
        expectedResponse.setMessage("Duck is deleted");
        validateResponse(runner,expectedResponse);
    }

}
