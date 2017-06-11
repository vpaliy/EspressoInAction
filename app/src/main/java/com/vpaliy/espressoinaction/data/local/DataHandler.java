package com.vpaliy.espressoinaction.data.local;

import java.util.List;

public interface DataHandler<T> {
    T fetchById(int id);
    void insert(T item);
    List<T> fetchAll();
    void delete(T item);
}
