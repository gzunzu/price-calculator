package org.gzunzu.adapter.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RateRs implements BasicCrudRs, Serializable {

    @Serial
    private static final long serialVersionUID = 2476743068040106390L;

    @JsonProperty(value = "id", required = true)
    private Long id;

    @JsonProperty(value = "name", required = true)
    private String name;
}
