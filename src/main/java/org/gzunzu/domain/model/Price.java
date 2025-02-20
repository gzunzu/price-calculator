package org.gzunzu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "price", indexes = {
        @Index(name = "idx_brand_product_dates", columnList = "brand_id, product_id, start_date, end_date"),
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Price implements BasicEntity<Price, Long>, Serializable {

    @Serial
    private static final long serialVersionUID = -8793392923677497920L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_seq")
    @SequenceGenerator(name = "price_seq", sequenceName = "price_seq", allocationSize = 1, initialValue = 5)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rate_id", nullable = false)
    private Rate rate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, name = "start_date")
    private LocalDateTime startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDateTime endDate;

    @Column(nullable = false)
    @PositiveOrZero(message = "Amount must be 0 or a positive decimal value.")
    private Float amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(nullable = false)
    @PositiveOrZero(message = "Priority must be 0 or a positive integer value.")
    private Integer priority;

    @SuppressWarnings("java:S107")
    public Price(final Brand brand,
                 final Rate rate,
                 final Currency currency,
                 final Product product,
                 final Float amount,
                 final LocalDateTime startDate,
                 final LocalDateTime endDate,
                 final Integer priority) {
        this.brand = brand;
        this.rate = rate;
        this.currency = currency;
        this.product = product;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return id.equals(price.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    @SuppressWarnings("java:S3776")
    public void merge(final Price other) {
        if (other == null) {
            return;
        }
        this.brand = ObjectUtils.notEqual(this.brand, other.brand) && other.brand != null && other.brand.getId() != null ? other.brand : this.brand;
        this.rate = ObjectUtils.notEqual(this.rate, other.rate) && other.rate != null && other.rate.getId() != null ? other.rate : this.rate;
        this.product = ObjectUtils.notEqual(this.product, other.product) && other.product != null && other.product.getId() != null ? other.product : this.product;
        this.startDate = ObjectUtils.notEqual(this.startDate, other.startDate) && other.startDate != null ? other.startDate : this.startDate;
        this.endDate = ObjectUtils.notEqual(this.endDate, other.endDate) && other.endDate != null ? other.endDate : this.endDate;
        this.amount = ObjectUtils.notEqual(this.amount, other.amount) && other.amount != null ? other.amount : this.amount;
        this.currency = ObjectUtils.notEqual(this.currency, other.currency) && other.currency != null && other.currency.getId() != null ? other.currency : this.currency;
        this.priority = ObjectUtils.notEqual(this.priority, other.priority) && other.priority != null ? other.priority : this.priority;
    }
}
