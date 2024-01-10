package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rest.domain.Accommodation;
import rest.domain.enumerations.AccommodationType;

import javax.transaction.Transactional;
import java.util.Collection;

public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {

    @Query("select a from Accommodation a where a.accommodetionType= ?1")
    public Collection<Accommodation> findAccommodationType(AccommodationType type);

    @Query("select a from Accommodation a where a.longitude= ?1 and a.latitude = ?2")
    public Collection<Accommodation> findAccommodationLocation(double laongitude, double latitude);
    @Query("select a from Accommodation a where a.owner.id=?1")
    public Collection<Accommodation> findAccommodationsOwned(Long ownerId );


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM benefits_mapping WHERE accommodation_id = :accommodationId", nativeQuery = true)
    void deleteBenefitsByAccommodationId(@Param("accommodationId") Long accommodationId);



}
