package com.palonskiy.dao;

import java.util.List;

public interface CrudDao<T> {
    List<T> getAll();

    void delete(Long id);

    void update(T obj);

    Long add(T obj);

    T getById(Long id);
}
