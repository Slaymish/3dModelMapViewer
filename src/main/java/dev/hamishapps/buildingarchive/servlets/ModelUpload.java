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

@WebServlet(name = "UploadBuilding", urlPatterns = "/upload/building")
public class ModelUpload extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Upload a building</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Upload Model!!!!</h1>");
        out.println("<form action=\"/upload/building\" method=\"post\">");
        out.println("<label>Model (obj/fbx):</label>");
        out.println("<input type=\"file\" name=\"model\" size=\"30\">");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // process form
        processForm(request, response);

        response.sendRedirect("/building");
    }

    private void processForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Building building = Persistency.getMostRecentDataPoint().getBuilding();

        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);

            if (value != null && value.isEmpty()) {
                continue;
            }

            if (name.equals("model")) {
                building.setModelPath(value);
            }
        }

        if (building.hasBuilding()){
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
