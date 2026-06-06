package autotests.payloads.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuckPropertiesResponse {

    //не поняла, как по итогу быть с id
    @JsonProperty("id")
    private Long id;

    @JsonProperty("color")
    private String color;

    @JsonProperty("height")
    private double height;

    @JsonProperty("material")
    private String material;

    @JsonProperty("sound")
    private String sound;

    @JsonProperty("wingsState")
    private String wingsState;
}
