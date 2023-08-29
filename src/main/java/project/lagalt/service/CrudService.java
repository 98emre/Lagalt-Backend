package project.lagalt.service;

import java.util.Collection;

public interface CrudService<T, ID> {

    Collection<T> findAll();

    T findById(ID id);

    T add(T entity);

    T update(T entity);

    void deleteById(ID id);
}
