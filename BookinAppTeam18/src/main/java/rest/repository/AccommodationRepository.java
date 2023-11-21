package rest.repository;

import org.springframework.stereotype.Repository;
import rest.domain.Accommodation;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AccommodationRepository implements IRepository<Accommodation>{

    private  static AtomicLong counter = new AtomicLong();
    private final ConcurrentMap<Long, Accommodation> accommodations = new ConcurrentHashMap<Long, Accommodation>();

    @Override
    public Collection<Accommodation> findAll() {
        return this.accommodations.values();
    }

    @Override
    public Accommodation create(Accommodation accommodation) {
        Long id = accommodation.getId();

        if(id == null){
            id = counter.incrementAndGet();
            accommodation.setId(id);
        }

        this.accommodations.put(id, accommodation);
        return accommodation;
    }

    @Override
    public Accommodation findOne(Long id) {
        return this.accommodations.get(id);
    }

    @Override
    public Accommodation update(Accommodation accommodation) {
        Long id = accommodation.getId();

        if(id != null){
            this.accommodations.put(id, accommodation);
        }
        return accommodation;

    }

    @Override
    public void delete(Long id) {
        this.accommodations.remove(id);
    }
}
