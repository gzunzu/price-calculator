package org.gzunzu.adapter.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PricePriorityRs implements BasicCrudRs, Serializable {

    @Serial
    private static final long serialVersionUID = 8545558688755893438L;

    @JsonProperty(value = "brandId", required = true)
    private Integer brandId;

    @JsonProperty(value = "productId", required = true)
    private Long productId;

    @JsonProperty(value = "rateId", required = true)
    private Long rateId;

    @JsonProperty(value = "startDate", required = true)
    private LocalDateTime startDate;

    @JsonProperty(value = "endDate", required = true)
    private LocalDateTime endDate;

    @JsonProperty(value = "amount", required = true)
    private Float amount;

    @JsonProperty(value = "currencyName", required = true)
    private String currencyName;
}
