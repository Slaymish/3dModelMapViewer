package dev.hamishapps.buildingarchive.servlets;

import com.google.gson.Gson;
import dev.hamishapps.buildingarchive.DataPoint;
import dev.hamishapps.buildingarchive.Persistency;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "BuildingManager", urlPatterns = "/buildings")
public class BuildingManager extends HttpServlet {
    private final List<DataPoint> buildings = Persistency.getDataPoints();  // Assume Persistency is handling storage

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Set the response type to JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Convert the list of buildings to JSON
        Gson gson = new Gson();
        String json = gson.toJson(buildings);

        // Write the JSON response
        resp.getWriter().write(json);
    }
}
