package com.ph.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "advert_types")
public class AdvertType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    /**
     * Entity relationships start
     */

    @OneToMany(mappedBy = "advertType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
