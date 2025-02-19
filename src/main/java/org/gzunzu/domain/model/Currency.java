package org.gzunzu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "currency")
@NoArgsConstructor
@Getter
@Setter
public class Currency implements BasicEntity<Currency, Integer>, Serializable {

    @Serial
    private static final long serialVersionUID = -4936274742342038142L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq")
    @SequenceGenerator(name = "currency_seq", sequenceName = "currency_seq", allocationSize = 1, initialValue = 4)
    private Integer id;

    @Column(nullable = false, unique = true, length = 3)
    @NotBlank(message = "Currency code must not be null nor blank.")
    @Size(min = 3, max = 3, message = "Currency code must follow the ISO 4217 standard of 3 characters.")
    private String name;

    public Currency(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return id.equals(currency.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void merge(final Currency other) {
        if (other == null) {
            return;
        }
        this.name = ObjectUtils.defaultIfNull(other.name, this.name);
    }
}
