package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.domain.Accommodation;
import rest.domain.Account;
import rest.domain.Reservation;

import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select a from Accommodation a join Reservation  r where a.id = ?1 and a.id=r.accountId")
    public Collection<Accommodation> findUserAccommodations(Long userId);
    @Query("select a from Account a join Reservation r where r.accommodationId=a.id and a.id=?1")
    public Collection<Account> findAccommodationGuests(Long accommodationId);
    @Query("select r from Reservation r where r.accountId=?1 order by r.id asc")
    public Collection<Reservation> findGuestReservations(Long guestId);
    @Query("select r from Reservation r where r.accommodationId=?1 order by r.id asc")
    public Collection<Reservation> findByAccommodationId(Long accommodationId);
    @Query("SELECT r FROM Reservation r JOIN Accommodation a ON r.accommodationId = a.id WHERE a.owner.id = ?1 ORDER BY r.id ASC")
    Collection<Reservation> findOwnerReservations(Long ownerId);
}