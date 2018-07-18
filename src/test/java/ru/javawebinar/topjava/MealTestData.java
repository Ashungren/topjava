package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL1_ID = AbstractBaseEntity.START_SEQ + 2;

    public static final Meal MEAL1 = new Meal(MEAL1_ID,
            LocalDateTime.of(2018, 7, 15, 8, 0), "breakfast", 1000);
    public static final Meal MEAL2 = new Meal(MEAL1_ID + 1,
            LocalDateTime.of(2018, 7, 15, 15, 0), "dinner", 800);
    public static final Meal MEAL3 = new Meal(MEAL1_ID + 2,
            LocalDateTime.of(2018, 7, 16, 8, 0), "breakfast", 900);
    public static final Meal ADMIN_MEAL1 = new Meal(MEAL1_ID + 3,
            LocalDateTime.of(2018, 7, 15, 8, 0), "admin breakfast", 900);
    public static final Meal ADMIN_MEAL2 = new Meal(MEAL1_ID + 4,
            LocalDateTime.of(2018, 7, 15, 15, 0), "admin dinner", 1000);

    public static void assertMealMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
