package autotests.payloads.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoundResponse {
    @JsonProperty("sound")
    private String sound;
}
