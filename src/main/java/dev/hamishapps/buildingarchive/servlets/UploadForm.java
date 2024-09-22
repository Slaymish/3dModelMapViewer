package dev.hamishapps.buildingarchive.servlets;

import dev.hamishapps.buildingarchive.Building;
import dev.hamishapps.buildingarchive.DataPoint;
import dev.hamishapps.buildingarchive.Persistency;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "UploadForm", urlPatterns = "/upload")
public class UploadForm extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // process form
        processForm(request, response);

        response.sendRedirect("/upload/building");
    }

    private void processForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Building building = new Building();

        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);

            if (value != null && value.isEmpty()) {
                continue;
            }

            switch (name) {
                case "author":
                    building.setAuthor(value);
                    break;
                case "description":
                    building.setDescription(value);
                    break;
                case "name":
                    building.setName(value);
                    break;
                case "longitude":
                    building.setLongitude(Double.valueOf(value));
                    break;
                case "latitude":
                    building.setLatitude(Double.valueOf(value));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown parameter: " + name);
            }
        }

        if (building.hasEnoughParams()) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            Persistency.addDataPoint(new DataPoint(building));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
