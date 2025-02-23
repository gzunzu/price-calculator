package org.gzunzu.adapter.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class PriceRs implements BasicCrudRs, Serializable {

    @Serial
    private static final long serialVersionUID = 8545558688755893438L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "brand", required = true)
    private BrandRs brand;

    @JsonProperty(value = "product", required = true)
    private ProductRs product;

    @JsonProperty(value = "rate", required = true)
    private RateRs rate;

    @JsonProperty(value = "startDate", required = true)
    private LocalDateTime startDate;

    @JsonProperty(value = "endDate", required = true)
    private LocalDateTime endDate;

    @JsonProperty(value = "amount", required = true)
    private Float amount;

    @JsonProperty(value = "currency", required = true)
    private CurrencyRs currency;

    @JsonProperty(value = "priority", required = true)
    private Integer priority;
}
