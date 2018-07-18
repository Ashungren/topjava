package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(MEAL1_ID, START_SEQ);
        assertMealMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        mealService.get(MEAL1_ID, START_SEQ + 1);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(MEAL1_ID, START_SEQ);
        assertMealsListMatch(mealService.getAll(START_SEQ), Arrays.asList(MEAL3, MEAL2));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        mealService.delete(MEAL1_ID, START_SEQ + 1);
    }

    @Test
    public void getBetweenDates() throws Exception {
        assertMealsListMatch(mealService.getBetweenDates(LocalDate.of(2018, 7, 15),
                LocalDate.of(2018, 7, 15), START_SEQ), Arrays.asList(MEAL2, MEAL1));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        assertMealsListMatch(mealService.getBetweenDateTimes(LocalDateTime.of(2018, 7, 15, 7, 0),
                LocalDateTime.of(2018, 7, 16, 23, 0), START_SEQ),
                Arrays.asList(MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void getAll() throws Exception {
        assertMealsListMatch(mealService.getAll(START_SEQ), Arrays.asList(MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void update() throws Exception {
        Meal meal = new Meal(MEAL1);
        meal.setDescription("updated");
        mealService.update(meal, START_SEQ);
        assertMealMatch(mealService.get(MEAL1_ID, START_SEQ), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal meal = new Meal(MEAL1);
        meal.setDescription("updated");
        mealService.update(meal, START_SEQ + 1);
    }

    @Test
    public void create() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2018, 7, 16, 20, 0),
                "test create", 500);
        mealService.create(meal, START_SEQ);
        assertMealsListMatch(mealService.getAll(START_SEQ), Arrays.asList(meal, MEAL3, MEAL2, MEAL1));
    }
}