package com.SafetyNet.Alerts.repository;

import java.util.List;

public interface CrudRepository<T> {

    List<T> getAll();
    T save(T t);
    T findByKey(String key);
    void delete(T t);
}
