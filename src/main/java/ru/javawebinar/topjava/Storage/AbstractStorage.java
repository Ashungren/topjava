package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface AbstractStorage {
    Meal save(Meal meal);

    void delete(Integer id);

    Meal get(Integer id);

    List<Meal> getAll();
}