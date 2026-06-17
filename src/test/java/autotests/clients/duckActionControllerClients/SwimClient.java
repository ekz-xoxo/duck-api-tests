package autotests.clients.duckActionControllerClients;

import autotests.clients.DuckClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.validation.json.JsonPathMessageValidationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class SwimClient extends DuckClient {
    public void duckSwim(TestCaseRunner runner, String duckId){
        runner.$(http()
                .client(duckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", duckId));
    }
}


