package com.ph.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category_property_keys")
public class CategoryPropertyKey implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(name = "built_in")
    private boolean builtIn;

    /**
     * Entity relationships start
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false) // Every category property key must have a category
    private Category category;


    @OneToMany(mappedBy = "categoryPropertyKey", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    //TODO: check on the postman whether keys value has been deleted or not
    private List<CategoryPropertyValue> categoryPropertyValues;
    /**
     * Entity relationships end
     */

    /**
     * Equals and HashCode - ToString methods start
     */
    @Override
    public String toString() {
        return "CategoryPropertyKey{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", builtIn=" + builtIn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryPropertyKey that = (CategoryPropertyKey) o;
        return builtIn == that.builtIn && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(categoryPropertyValues, that.categoryPropertyValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, builtIn, category, categoryPropertyValues);
    }

    /**
     * Equals and HashCode - ToString methods end
     */

}
