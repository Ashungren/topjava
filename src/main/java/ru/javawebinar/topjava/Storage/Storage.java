package ru.javawebinar.topjava.Storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage implements AbstractStorage {
    private Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.MEALS) {
            save(meal);
        }
    }

    @Override
    public Meal save(Meal meal) {
        if (!exist(meal.getId())) {
            meal.setId(count.incrementAndGet());
        }
        return storage.put(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        if (exist(id)) {
            storage.remove(id);
        }
    }

    @Override
    public Meal get(Integer id) {
        if (exist(id)) {
            return storage.get(id);
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    private boolean exist(Integer id) {
        return !(id == null);
    }
}
