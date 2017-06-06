package com.vpaliy.espressoinaction.domain;


import java.util.List;
import rx.Observable;

public interface IRepository<T> {
    Observable<List<T>> getAll();
    Observable<T> getById(int id);
    void insert(T item);

}
