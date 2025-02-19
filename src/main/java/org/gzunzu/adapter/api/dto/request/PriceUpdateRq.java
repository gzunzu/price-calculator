package org.gzunzu.adapter.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.PositiveOrZero;
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
public class PriceUpdateRq implements BasicCrudUpdateRq, Serializable {

    @Serial
    private static final long serialVersionUID = 6615811948277517855L;

    @JsonProperty(value = "brandId")
    @PositiveOrZero(message = "Brand ID must be 0 or a positive integer value.")
    private Integer brandId;

    @JsonProperty(value = "productId")
    @PositiveOrZero(message = "Product ID must be 0 or a positive integer value.")
    private Long productId;

    @JsonProperty(value = "rateId")
    @PositiveOrZero(message = "Rate ID must be 0 or a positive integer value.")
    private Long rateId;

    @JsonProperty(value = "startDate")
    private LocalDateTime startDate;

    @JsonProperty(value = "endDate")
    private LocalDateTime endDate;

    @JsonProperty(value = "amount")
    @PositiveOrZero(message = "Amount must be 0 or a positive decimal value.")
    private Float amount;

    @JsonProperty(value = "currencyId")
    @PositiveOrZero(message = "Currency ID must be 0 or a positive integer value.")
    private Integer currencyId;

    @JsonProperty(value = "priority")
    @PositiveOrZero(message = "Priority must be 0 or a positive integer value.")
    private Integer priority;
}
