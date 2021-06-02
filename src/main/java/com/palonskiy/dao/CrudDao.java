package com.palonskiy.dao;

import java.util.List;

public interface CrudDao<T> {
    List<T> getAll();

    void delete(T obj);

    void update(T obj);

    T add(T obj);

    T getById(Long id);

    List<T> getByField(Object obj, String field);
}
