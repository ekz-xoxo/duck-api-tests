package autotests.payloads.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    @JsonProperty("message")
    private String message;
}