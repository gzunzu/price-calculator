package org.gzunzu.adapter.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class PricePriorityRq implements Serializable {

    @Serial
    private static final long serialVersionUID = -2687863394042584277L;

    @JsonProperty(value = "brandId", required = true)
    @NotNull(message = "Brand ID must not be null.")
    @PositiveOrZero(message = "Brand ID must be 0 or a positive integer value.")
    private Integer brandId;

    @JsonProperty(value = "productId", required = true)
    @NotNull(message = "Product ID must not be null.")
    @PositiveOrZero(message = "Product ID must be 0 or a positive integer value.")
    private Long productId;

    @JsonProperty(value = "purchaseDatetime", required = true)
    @NotNull(message = "Purchase datetime ID must not be null.")
    private LocalDateTime purchaseDatetime;
}
