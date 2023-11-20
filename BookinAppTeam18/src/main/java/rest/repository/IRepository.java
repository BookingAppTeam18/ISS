package rest.repository;

import rest.domain.Greeting;

import java.util.Collection;

public interface IRepository<T> {

    Collection<T> findAll();

    T create(T object);

    T findOne(Long id);

    T update(T object);

    void delete(Long id);
}
