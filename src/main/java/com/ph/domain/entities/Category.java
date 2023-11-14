package com.ph.domain.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ph.domain.abstracts.Entry;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@ToString(callSuper = true) // toString de bazen iliskili tablolarda recursive yapiya giriyor bunuda JsonIgnore ile cozuyoruz.
public class Category extends Entry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 150, nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, length = 50)
    private String icon;

    @Column(name = "seq")
    private int seq;

    @Column(name = "built_in")
    private boolean builtIn;

    //Soru: wrapper mi primitive mi olarak ayarlanmalı?
    @Column(name = "is_active")
    private boolean active;  // TODO: isActive getter methodunda sorun oluşturdugundan active olarak değiştirildi.
    /**
     * Entity relationships start
     */
    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Advert> adverts;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CategoryPropertyKey> categoryPropertyKeys;
    /**
     * Entity relationships end
     */
    /**
     * Equals and HashCode - ToString methods start
     */
    //Not: ToString Lombok'tan gelen anotasyon ile yazildi.


    // TODO:isActive olanları active yaptım
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return seq == category.seq && builtIn == category.builtIn && active == category.active && Objects.equals(id, category.id) && Objects.equals(title, category.title) && Objects.equals(slug, category.slug) && Objects.equals(icon, category.icon) && Objects.equals(adverts, category.adverts) && Objects.equals(categoryPropertyKeys, category.categoryPropertyKeys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, slug, icon, seq, builtIn, active, adverts, categoryPropertyKeys);
    }

    /**
     * Equals and HashCode - ToString methods end
     */
}