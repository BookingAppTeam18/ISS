package rest.service;

import java.util.Collection;

public interface IService<T> {

	Collection<T> findAll();

	T findOne(Long id);

	T insert(T greeting) throws Exception;

	T update(T greeting) throws Exception;

	T delete(Long id);

	void deleteAll();
}