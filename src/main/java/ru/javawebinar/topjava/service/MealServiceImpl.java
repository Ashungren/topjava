package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Meal userMeal = repository.save(meal, userId);
        if (userMeal == null) {
            throw new NotFoundException("Wrong userId in save attempt");
        }
        return userMeal;
    }

    @Override
    public Meal update(Meal meal, int userId) {
        Meal userMeal = repository.save(meal, userId);
        if (userMeal == null) {
            throw new NotFoundException("Wrong userId in update attempt");
        }
        return userMeal;
    }

    @Override
    public void delete(int id, int userId) {
        if (!repository.delete(id, userId)) {
            throw new NotFoundException("Wrong userId in delete attempt");
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal userMeal = repository.get(id, userId);
        if (userMeal == null) {
            throw new NotFoundException("Wrong userId in get attempt");
        }
        return userMeal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}