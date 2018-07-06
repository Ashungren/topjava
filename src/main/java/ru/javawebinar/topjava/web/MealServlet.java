package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Storage.Storage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new Storage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Meal meal;
        int id;

        if (action == null) {
            log.debug("redirect to meals list");
            request.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(storage.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case ("create"):
                log.info("create meal");
                meal = new Meal(LocalDateTime.now(), "", 1000);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                break;
            case ("update"):
                id = Integer.valueOf(request.getParameter("id"));
                log.info("update meal {}", id);
                meal = storage.get(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
                break;
            case ("delete"):
                id = Integer.valueOf(request.getParameter("id"));
                log.info("delete meal {}", id);
                storage.delete(id);
                response.sendRedirect("meals");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal;
        if (id.isEmpty()) {
            meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"), Integer.valueOf(request.getParameter("calories")));
            log.info("create {}", meal);
        } else {
            meal = new Meal(Integer.valueOf(id), LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"), Integer.valueOf(request.getParameter("calories")));
            log.info("update {}", meal);
        }
        storage.save(meal);
        response.sendRedirect("meals");
    }
}
