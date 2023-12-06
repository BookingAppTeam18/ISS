package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.domain.Accommodation;
import rest.domain.Reservation;
import rest.domain.Account;

import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select a from Accommodation a join Reservation  r where a.id = ?1 and a.id=r.accountId")
    public Collection<Accommodation> findUserAccommodations(Long userId);

    @Query("select a from Account a join Reservation r where r.accommodationId=a.id and a.id=?1")

    public Collection<Account> findAccommodationGuests(Long accommodationId);
    @Query("select r from Reservation r where r.accountId=?1")

    public Collection<Reservation> findGuestReservations(Long guestId);
}