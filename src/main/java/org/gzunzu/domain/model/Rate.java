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
@Table(name = "rate")
@NoArgsConstructor
@Getter
@Setter
public class Rate implements BasicEntity<Rate, Long>, Serializable {

    @Serial
    private static final long serialVersionUID = -8215466150796552507L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rate_seq")
    @SequenceGenerator(name = "rate_seq", sequenceName = "rate_seq", allocationSize = 1, initialValue = 5)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Name must not be null nor blank.")
    private String name;

    public Rate(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return id.equals(rate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void merge(final Rate other) {
        if (other == null) {
            return;
        }
        this.name = ObjectUtils.defaultIfNull(other.name, this.name);
    }
}
