package autotests.clients.duckControllerClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DeleteClient extends DuckClient {
    @Step("Удвляем уточку")
    public void deleteDuck(TestCaseRunner runner, String duckId) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", duckId));
    }
}
