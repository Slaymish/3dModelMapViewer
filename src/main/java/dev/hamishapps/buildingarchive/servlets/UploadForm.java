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
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Upload a building</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet UploadForm!!!!</h1>");
        out.println("<form action=\"upload\" method=\"post\">");
        out.println("<label>Name:</label>");
        out.println("<input type=\"text\" name=\"name\" size=\"30\">");
        out.println("<label>Author's name:</label>");
        out.println("<input type=\"text\" name=\"author\" size=\"30\">");
        out.println("<label>Description:</label>");
        out.println("<input type=\"text\" name=\"description\" size=\"30\">");
        out.println("<label>Latitude:</label>");
        out.println("<input type=\"number\" name=\"latitude\" size=\"30\">");
        out.println("<label>Longitude:</label>");
        out.println("<input type=\"number\" name=\"longitude\" size=\"30\">");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // process form
        processForm(request, response);

        response.sendRedirect("index.html");
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
