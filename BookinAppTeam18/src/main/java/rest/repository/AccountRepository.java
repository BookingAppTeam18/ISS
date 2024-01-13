package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rest.domain.Accommodation;
import rest.domain.Account;
import rest.domain.enumerations.AccommodationType;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT accommodation FROM Account a JOIN a.favouriteAccommodations accommodation WHERE a.id = :accountId")
    public Collection<Accommodation> findFavouriteAccommodation(@Param("accountId") Long accountId);

    @Query("SELECT account FROM Account account where account.email=?1")
    Account findByEmail(String email);

    @Query(value = "SELECT a.* FROM accommodations a " +
            "JOIN account_favourite_accommodations afa ON a.id = afa.accommodation_id " +
            "WHERE afa.account_id = :accountId", nativeQuery = true)
    Collection<Object[]> findFavouriteAccommodationsByAccountId(@Param("accountId") Long accountId);

    //U odnosu na id usera dobiti njegovu listu accommodationa i onda dodati/ukloniti accommodation
    // sa odredjenim idom (proslijediti id accommodationa i accounta)


}
