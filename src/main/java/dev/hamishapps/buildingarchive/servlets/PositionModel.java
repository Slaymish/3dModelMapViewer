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

@WebServlet(name = "PositionModel", urlPatterns = "/building")
public class PositionModel extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Adjust Building Position</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"main.css\">");
        out.println("<script type=\"text/javascript\">");
        out.println("function sendCommand(command) {");
        out.println("    var form = document.getElementById('commandForm');");
        out.println("    form.command.value = command;");
        out.println("    form.submit();");
        out.println("}");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Adjust Building Position/Rotation</h1>");
        out.println("<form id='commandForm' action='/building' method='post'>");
        out.println("<input type='hidden' name='command' value=''/>");
        out.println("<button type='button' onclick=\"sendCommand('up')\">Up</button>");
        out.println("<button type='button' onclick=\"sendCommand('down')\">Down</button>");
        out.println("<button type='button' onclick=\"sendCommand('left')\">Left</button>");
        out.println("<button type='button' onclick=\"sendCommand('right')\">Right</button>");
        out.println("<button type='button' onclick=\"sendCommand('rotate')\">Rotate</button>");
        out.println("<button type='button' onclick=\"sendCommand('save')\">Save</button>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Process the movement/rotation commands
        String page = processForm(request, response);

        // Redirect to the same page to allow further adjustments
        response.sendRedirect(page);
    }

    private String processForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the command from the form (up, down, left, right, rotate)
        String command = request.getParameter("command");

        // Fetch the latest DataPoint (assuming you're fetching the current one to modify)
        DataPoint dataPoint = Persistency.getMostRecentDataPoint();

        String homePage = "/building";

        // Initialize the offsets array with at least two elements
        double[] offsets = dataPoint.getOffsets();
        if (offsets.length < 2) {
            // If the array is not properly initialized, ensure it has at least two elements
            offsets = new double[] {0.0, 0.0};
        }

        double rotationAngle = dataPoint.getRotationAngle();
        // Adjust the position or rotation of the building based on the command
        switch (command) {
            case "up":
                offsets[1] += 1;  // Move up by 1 unit
                break;
            case "down":
                offsets[1] -= 1;  // Move down by 1 unit
                break;
            case "left":
                offsets[0] -= 1;  // Move left by 1 unit
                break;
            case "right":
                offsets[0] += 1;  // Move right by 1 unit
                break;
            case "rotate":
                // Rotate the building (e.g., increment the rotation angle)
                rotationAngle += 90;  // Rotate by 90 degrees
                break;
            case "save":
                homePage = "/";
                break;
            default:
                // Handle invalid command
                break;
        }

        // Update the building offsets and rotation angle
        dataPoint.setOffsets(offsets);
        dataPoint.setRotationAngle(rotationAngle);

        // Persist the updated DataPoint
        Persistency.updateDataPoint(dataPoint);

        return homePage;
    }

}

