package org.gzunzu.adapter.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
public class PriceAddRq implements BasicCrudAddRq, Serializable {

    @Serial
    private static final long serialVersionUID = -5336409896618569006L;

    @JsonProperty(value = "brandId", required = true)
    @NotNull(message = "Brand ID must not be null.")
    @PositiveOrZero(message = "Brand ID must be 0 or a positive integer value.")
    private Integer brandId;

    @JsonProperty(value = "productId", required = true)
    @NotNull(message = "Product ID must not be null.")
    @PositiveOrZero(message = "Product ID must be 0 or a positive integer value.")
    private Long productId;

    @JsonProperty(value = "rateId", required = true)
    @NotNull(message = "Rate ID must not be null.")
    @PositiveOrZero(message = "Rate ID must be 0 or a positive integer value.")
    private Long rateId;

    @JsonProperty(value = "startDate", required = true)
    @NotNull(message = "Start date ID must not be null.")
    private LocalDateTime startDate;

    @JsonProperty(value = "endDate", required = true)
    @NotNull(message = "Start date ID must not be null.")
    private LocalDateTime endDate;

    @JsonProperty(value = "amount", required = true)
    @NotNull(message = "Amount must not be null.")
    @PositiveOrZero(message = "Amount must be 0 or a positive decimal value.")
    private Float amount;

    @JsonProperty(value = "currencyId", required = true)
    @NotNull(message = "Currency ID must not be null.")
    @PositiveOrZero(message = "Currency ID must be 0 or a positive integer value.")
    private Integer currencyId;

    @JsonProperty(value = "priority", required = true)
    @NotNull(message = "Priority must not be null.")
    @PositiveOrZero(message = "Priority must be 0 or a positive integer value.")
    private Integer priority;
}
