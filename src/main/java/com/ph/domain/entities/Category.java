package com.ph.domain.entities;

import com.ph.domain.abstracts.Entry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category extends Entry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String slug;
    private String icon;
    private int seq;
    private boolean builtIn;
    private boolean isActive;

    /**
     * Entity relationships start
     */

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Advert> adverts;
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
