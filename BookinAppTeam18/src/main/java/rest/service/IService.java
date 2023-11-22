package rest.service;
import java.util.Collection;

public interface IService<T> {

	Collection<T> findAll();

	T findOne(Long id);

	T create(T greeting) throws Exception;

	T update(T greeting) throws Exception;

	void delete(Long id);
	
}

