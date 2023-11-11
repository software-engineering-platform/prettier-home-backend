package com.ph.domain.entities;

import com.ph.domain.abstracts.Entry;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorites")
public class Favorite extends Entry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Entity relationships start
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advert_id")
    private Advert advert;
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
