package org.gzunzu.adapter.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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
public class RateUpdateRq implements BasicCrudUpdateRq, Serializable {

    @Serial
    private static final long serialVersionUID = 6001918377627534009L;

    @JsonProperty(value = "name", required = true)
    @NotBlank(message = "Name must not be null nor blank.")
    private String name;
}
