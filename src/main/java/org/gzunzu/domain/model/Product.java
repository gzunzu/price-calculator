package org.gzunzu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
@Setter
public class Product implements BasicEntity<Product, Long>, Serializable {

    @Serial
    private static final long serialVersionUID = -8347930141701939172L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1, initialValue = 2)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name must not be null nor blank.")
    private String name;

    public Product(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void merge(final Product other) {
        if (other == null) {
            return;
        }
        this.name = ObjectUtils.defaultIfNull(other.name, this.name);
    }
}
