package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rest.domain.Price;
import java.util.Collection;

public interface PriceRepository extends JpaRepository<Price,Long> {

    @Query("select p from Price p where p.accommodation.id= ?1")
    public Collection<Price> findPricesForAccommodation(Long accommodationId);

    @Query("select p from Price p where p.accommodation.id= ?1 AND p.start >= CURRENT_DATE  ORDER BY p.start ASC")
    public Price findNextPriceForAccommodation(Long accommodationId);

    @Modifying
    @Query("DELETE FROM Price p WHERE p.accommodation.id = :accommodationId")
    void deletePricesByAccommodationId(@Param("accommodationId") Long accommodationId);


}
