package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.domain.Accommodation;
import rest.domain.enumerations.AccommodationType;

import java.util.Collection;

public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {

    @Query("select a from Accommodation a where a.accommodetionType= ?1")
    public Collection<Accommodation> findAccommodationType(AccommodationType type);

    @Query("select a from Accommodation a where a.longitude= ?1 and a.latitude = ?2")
    public Collection<Accommodation> findAccommodationLocation(double laongitude, double latitude);
    @Query("select a from Accommodation a where a.owner.id=?1")
    public Collection<Accommodation> findAccommodationsOwned(Long ownerId );

}
