package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.domain.Accommodation;
import rest.domain.AccommodationRequest;
import rest.domain.enumerations.AccommodationState;
import rest.domain.enumerations.AccommodationType;

import java.util.Collection;

public interface AccommodationRequestRepository extends JpaRepository<AccommodationRequest,Long> {

    @Query("select a from AccommodationRequest a where a.accommodationState= ?1")
    public Collection<AccommodationRequest> findAccommodationRequestType(AccommodationState state);

}
