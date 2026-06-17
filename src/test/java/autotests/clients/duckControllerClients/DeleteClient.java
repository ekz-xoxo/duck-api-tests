package autotests.clients.duckControllerClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DeleteClient extends DuckClient {
    public void deleteDuck(TestCaseRunner runner, String duckId) {
        runner.$(http()
                .client(duckService)
                .send()
                .delete("/api/duck/delete")
                .queryParam("id", duckId));
    }
}
