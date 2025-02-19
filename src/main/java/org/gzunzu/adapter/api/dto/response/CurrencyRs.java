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
public class CurrencyRs implements BasicCrudRs, Serializable {

    @Serial
    private static final long serialVersionUID = 1261950729078266132L;

    @JsonProperty(value = "id", required = true)
    private Integer id;

    @JsonProperty(value = "name", required = true)
    private String name;
}
