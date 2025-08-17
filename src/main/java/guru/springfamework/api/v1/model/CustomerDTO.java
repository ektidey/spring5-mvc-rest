package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;

    @Schema(description = "Customer first name", example = "Joe", required = true)
    private String firstname;
    @Schema(description = "Customer last name", example = "Smith", required = true)
    private String lastname;

    @JsonProperty("customer_url")
    private String customerUrl;
}
