package rest.repository;

import java.util.Collection;
import rest.domain.Greeting;

public interface IRepository<T> {

    Collection<Greeting> findAll();

    T create(T object);

    T findOne(Long id);

    T update(T object);

    void delete(Long id);

}
