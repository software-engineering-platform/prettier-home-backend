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
@Table(name = "adverts")
public class Advert extends Entry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String slug;
    private Double price;
    private int status;
    private boolean builtIn;
    private boolean isActive;
    private int viewCount;
    private String location;

    /**
     * Entity relationships start
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advert_type_id")
    private AdvertType advertType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoryPropertyValue> categoryPropertyValues;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TourRequest> tourRequests;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Log> logs;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favorite> favorites;
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
