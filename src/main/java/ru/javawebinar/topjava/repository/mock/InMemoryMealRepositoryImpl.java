package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.UsersUtil.ADMIN_ID;
import static ru.javawebinar.topjava.util.UsersUtil.USER_ID;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Comparator<Meal> MEAL_COMPARATOR = (m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime());

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private Map<Meal, Integer> userMealIdBase = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), USER_ID);
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), USER_ID);

        save(new Meal(LocalDateTime.of(2018, Month.JULY, 9, 10, 0), "Завтрак админ", 1000), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2018, Month.JULY, 9, 20, 0), "Ужин админ", 500), ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Integer id = meal.getId();
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            userMealIdBase.put(meal, userId);
        } else if (get(id, userId) != null) {
            repository.put(id, meal);
            for (Meal m : userMealIdBase.keySet()) {
                if (Objects.equals(m.getId(), meal.getId())) {
                    userMealIdBase.remove(m);
                }
            }
            userMealIdBase.put(meal, userId);
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null) {
            for (Meal m : userMealIdBase.keySet()) {
                if (m.equals(meal) && userMealIdBase.get(m) == userId) {
                    userMealIdBase.remove(m);
                    repository.remove(id);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null) {
            for (Meal m : userMealIdBase.keySet()) {
                if (m.equals(meal) && userMealIdBase.get(m) == userId) {
                    return m;
                }
            }
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return userMealIdBase.keySet().stream().filter(m -> userMealIdBase.get(m) == userId).sorted(MEAL_COMPARATOR).collect(Collectors.toList());
    }
}