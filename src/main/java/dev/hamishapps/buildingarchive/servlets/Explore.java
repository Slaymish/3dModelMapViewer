package dev.hamishapps.buildingarchive.servlets;

import dev.hamishapps.buildingarchive.DataPoint;
import dev.hamishapps.buildingarchive.Persistency;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Explore", urlPatterns = "/explore")
public class Explore extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<DataPoint> data = Persistency.getDataPoints();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\">");
        out.println("<title>Explore Map</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Explore Map</h1>");

        for (DataPoint dp : data) {
            out.println(dp);
        }

        out.println("</body>");
        out.println("</html>");

    }
}