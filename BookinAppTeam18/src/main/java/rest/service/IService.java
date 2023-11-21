package rest.service;

import rest.domain.Greeting;

import java.util.Collection;

public interface IService <T>{
    Collection<T> findAll();

    T findOne(Long id);

    T create(T object) throws Exception;

    T update(T object) throws Exception;

    void delete(Long id);
}
