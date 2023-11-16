package com.ph.repository;

import com.ph.domain.entities.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertRepository extends JpaRepository<Advert,Long> {
    //Not: AÇIKLAMASI ALTTAKİ NOTTA BULUNMAKTADIR
     /*
    NOT: AÇIKLAMA
    SELECT a FROM Advert a: Bu kısım, Advert sınıfından (a takma adıyla) nesneleri seçiyoruz. Yani sorgu sonucu Advert türünden nesneleri içeren bir liste veya sayfa elde edeceğiz.

"(:query IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%'))) ": Bu kısım, :query parametresine dayalı bir başlık (title) filtresi ekler. Eğer query null ise, bu koşul dikkate alınmaz. Aksi takdirde, başlık içinde LIKE kullanılarak case-insensitive (LOWER fonksiyonu ile) bir arama yapılır.

"AND (:categoryId IS NULL OR a.category.id = :categoryId)": Bu kısım, :categoryId parametresine dayalı bir kategori filtresi ekler. Eğer categoryId null ise, bu koşul dikkate alınmaz. Aksi takdirde, kategori ID'sine göre eşleşen ilanlar seçilir.

"AND (:advertTypeId IS NULL OR a.advertType.id = :advertTypeId)": Bu kısım, :advertTypeId parametresine dayalı bir reklam türü filtresi ekler. Eğer advertTypeId null ise, bu koşul dikkate alınmaz. Aksi takdirde, reklam türü ID'sine göre eşleşen ilanlar seçilir.

"AND (:priceStart IS NULL OR a.price >= :priceStart)": Bu kısım, :priceStart parametresine dayalı bir fiyat başlangıcı filtresi ekler. Eğer priceStart null ise, bu koşul dikkate alınmaz. Aksi takdirde, belirtilen fiyat başlangıcından yüksek veya eşit fiyatlı ilanlar seçilir.

"AND (:priceEnd IS NULL OR a.price <= :priceEnd)": Bu kısım, :priceEnd parametresine dayalı bir fiyat bitişi filtresi ekler. Eğer priceEnd null ise, bu koşul dikkate alınmaz. Aksi takdirde, belirtilen fiyat bitişinden düşük veya eşit fiyatlı ilanlar seçilir.

"AND (:status IS NULL OR a.status = :status)": Bu kısım, :status parametresine dayalı bir durum filtresi ekler. Eğer status null ise, bu koşul dikkate alınmaz. Aksi takdirde, belirtilen duruma sahip ilanlar seçilir.

Pageable pageable: Sayfalama ve sıralama işlemlerini yapmak için Pageable nesnesi kullanılır.
     */
    /*NOT: AÇIKLAMA
      "AND (:categoryId IS NULL OR a.category.id = :categoryId) " +
            "AND (:advertTypeId IS NULL OR a.advertType.id = :advertTypeId) " +
            BİR ÖNCEKİ HALİ
     */
    @Query("SELECT a FROM Advert a WHERE " +
            "(:query IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(a.description) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND (:categoryId IS NULL OR a.category.id = :categoryId) " +
            "AND (:advertTypeId IS NULL OR a.advertType.id = :advertTypeId) " +
            "AND (:priceStart IS NULL OR a.price >= :priceStart) " +
            "AND (:priceEnd IS NULL OR a.price <= :priceEnd) " +
            "AND (:status IS NULL OR a.status = :status)")

    Page<Advert> findByAdminPage(
            @Param("query") String query,
            @Param("categoryId") Long categoryId,
            @Param("advertTypeId") Long advertTypeId,
            @Param("priceStart") Integer priceStart,
            @Param("priceEnd") Integer priceEnd,
            @Param("status") Integer status,
            Pageable pageable
    );

    //Start: categoryService için yazıldı
    List<Advert> findByCategory_Id(Long categoryId);
    //Finish: categoryService için yazıldı



    @Query("SELECT a.city.name, COUNT(a.id) FROM Advert a GROUP BY a.city.name")
    List<Object[]> getAdvertsByCities();

    @Query("SELECT a.category.title, COUNT(a.id) FROM Advert a GROUP BY a.category.title")
    List<Object[]> getAdvertsByCategories();


    @Query("SELECT a FROM Advert a LEFT JOIN a.tourRequests t GROUP BY a ORDER BY (3 * COUNT(t) + a.viewCount) DESC")
    public Page<Advert> findPopularAdverts(Pageable pageable);

    //NOT: ESKİ QUERY (EKSİK)
/*
//    @Query("""
//            select a from Advert a
//            where upper(a.title) like upper(concat('%', ?1, '%')) or upper(a.description) like upper(concat('%', ?1, '%'))""")
//    Page<Advert> searchAdvertByPage(@Nullable String query , Pageable pageable);
 */







    Page<Advert> findByUser_Id(Long id, Pageable pageable);

    Optional<Advert> findBySlug(String slug);
}
