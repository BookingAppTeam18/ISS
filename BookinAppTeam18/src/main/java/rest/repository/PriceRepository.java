package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.domain.Price;
import java.util.Collection;

public interface PriceRepository extends JpaRepository<Price,Long> {

    @Query("select p from Price p where p.accommodation.id= ?1")
    public Collection<Price> findPricesForAccommodation(Long accommodationId);

}
