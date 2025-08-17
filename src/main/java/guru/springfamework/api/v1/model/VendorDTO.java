package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VendorDTO {

    @Schema(description = "Vendor name", example = "My Vendor", required = true)
    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;
}
