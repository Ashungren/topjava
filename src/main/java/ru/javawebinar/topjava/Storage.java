package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger(0);

    {
        for (Meal meal : MealsUtil.MEALS) {
            save(meal);
        }
    }

    public void save(Meal meal) {
        if (!exist(meal.getId())) {
            meal.setId(count.incrementAndGet());
            storage.put(meal.getId(), meal);
        } else {
            update(meal, meal.getId());
        }
    }

    public void update(Meal meal, Integer id) {
        if (exist(id)) {
            storage.put(id, meal);
        } else {
            save(meal);
        }
    }

    public void delete(Integer id) {
        if (exist(id)) {
            storage.remove(id);
        }
    }

    public Meal get(Integer id) {
        if (exist(id)) {
            return storage.get(id);
        }
        return null;
    }

    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    private boolean exist(Integer id) {
        return !(id == null);
    }
}
