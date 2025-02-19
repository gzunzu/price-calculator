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
@Table(name = "brand")
@NoArgsConstructor
@Getter
@Setter
public class Brand implements BasicEntity<Brand, Integer>, Serializable {

    @Serial
    private static final long serialVersionUID = 4029793110497171423L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq")
    @SequenceGenerator(name = "brand_seq", sequenceName = "brand_seq", allocationSize = 1, initialValue = 2)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name must not be null nor blank.")
    private String name;

    public Brand(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return id.equals(brand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void merge(final Brand other) {
        if (other == null) {
            return;
        }
        this.name = ObjectUtils.defaultIfNull(other.name, this.name);
    }
}
