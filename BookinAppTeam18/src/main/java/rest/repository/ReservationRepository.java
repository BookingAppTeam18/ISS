package rest.repository;

import rest.domain.Reservation;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationRepository implements IRepository<Reservation>{
    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, Reservation> reservations = new ConcurrentHashMap<Long, Reservation>();

    @Override
    public Collection<Reservation> findAll() {
        return this.reservations.values();
    }

    @Override
    public Reservation create(Reservation reservation) {
        Long id = reservation.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            reservation.setId(id);
        }

        this.reservations.put(id, reservation);
        return reservation;
    }

    @Override
    public Reservation findOne(Long id) {
        return this.reservations.get(id);
    }

    @Override
    public void delete(Long id) {
        this.reservations.remove(id);
    }

    @Override
    public Reservation update(Reservation reservation) {
        Long id = reservation.getId();

        if (id != null) {
            this.reservations.put(id, reservation);
        }

        return reservation;
    }
}
