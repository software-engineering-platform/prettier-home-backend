package com.ph.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

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

    private String name;
    private boolean builtIn;

    /**
     * Entity relationships start
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "categoryPropertyKey", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoryPropertyValue> categoryPropertyValues;
    /**
     * Entity relationships end
     */

    /**
     * Equals and HashCode - ToString methods start
     */

    /**
     * Equals and HashCode - ToString methods end
     */

}
