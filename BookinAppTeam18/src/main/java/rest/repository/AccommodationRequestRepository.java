package rest.repository;

import org.springframework.stereotype.Repository;
import rest.domain.AccommodationRequest;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AccommodationRequestRepository implements IRepository<AccommodationRequest> {
    private static AtomicLong counter = new AtomicLong();

    private final ConcurrentMap<Long, AccommodationRequest> accommodationRequests = new ConcurrentHashMap<Long, AccommodationRequest>();

    @Override
    public Collection<AccommodationRequest> findAll() {
        return this.accommodationRequests.values();
    }

    @Override
    public AccommodationRequest create(AccommodationRequest accommodationRequest) {
        Long id = accommodationRequest.getId();

        if(id == null){
            id = counter.incrementAndGet();
            accommodationRequest.setId(id);
        }

        this.accommodationRequests.put(id, accommodationRequest);
        return accommodationRequest;
    }

    @Override
    public AccommodationRequest findOne(Long id) {
        return this.accommodationRequests.get(id);
    }

    @Override
    public AccommodationRequest update(AccommodationRequest accommodationRequest) {
        Long id = accommodationRequest.getId();

        if(id != null){
            this.accommodationRequests.put(id, accommodationRequest);
        }
        return accommodationRequest;

    }

    @Override
    public void delete(Long id) {
        this.accommodationRequests.remove(id);
    }
}
