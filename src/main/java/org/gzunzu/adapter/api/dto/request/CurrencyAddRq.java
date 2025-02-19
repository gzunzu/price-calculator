package org.gzunzu.adapter.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CurrencyAddRq implements BasicCrudAddRq, Serializable {

    @Serial
    private static final long serialVersionUID = -947288934034252438L;

    @JsonProperty(value = "name", required = true)
    @NotBlank(message = "Name must not be null nor blank.")
    @Size(min = 3, max = 3, message = "Currency code must follow the ISO 4217 standard of 3 characters.")
    private String name;
}
