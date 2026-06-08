package autotests;

import com.consol.citrus.http.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import com.consol.citrus.http.client.HttpClient;

public class EndpointConfig {
    @Bean("duckService")
    public HttpClient duckService(){
    return new HttpClientBuilder()
            .requestUrl("http://localhost:2222/")
            .build();

    }

}
