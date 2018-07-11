package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        checkNew(meal);
        return repository.save(meal, userId);
    }

    @Override
    public Meal update(Meal meal, int userId) {
        return checkNotFoundWithId(repository.save(meal, userId), userId);
    }

    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), userId);
    }

    @Override
    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}