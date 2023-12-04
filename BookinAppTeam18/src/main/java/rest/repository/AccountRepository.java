package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rest.domain.Accommodation;
import rest.domain.Account;
import rest.domain.enumerations.AccommodationType;

import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT accommodation FROM Account a JOIN a.favouriteAccommodations accommodation WHERE a.id = :accountId")
    public Collection<Accommodation> findFavouriteAccommodation(@Param("accountId") Long accountId);


}
