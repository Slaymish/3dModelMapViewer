package dev.hamishapps.buildingarchive.servlets;

import com.google.gson.Gson;
import dev.hamishapps.buildingarchive.Camera;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CameraServlet", urlPatterns = "/camera")
public class CameraServlet extends HttpServlet {

    private Camera camera = new Camera(0, 0, 10, 0, 0, 0);  // Default camera settings

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        // Convert the camera object to JSON
        Gson gson = new Gson();
        String cameraJson = gson.toJson(camera);

        out.print(cameraJson);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Parse camera position and rotation from request parameters
        double posX = Double.parseDouble(req.getParameter("posX"));
        double posY = Double.parseDouble(req.getParameter("posY"));
        double posZ = Double.parseDouble(req.getParameter("posZ"));
        double rotX = Double.parseDouble(req.getParameter("rotX"));
        double rotY = Double.parseDouble(req.getParameter("rotY"));
        double rotZ = Double.parseDouble(req.getParameter("rotZ"));

        // Update the camera object
        camera.setPosX(posX);
        camera.setPosY(posY);
        camera.setPosZ(posZ);
        camera.setRotX(rotX);
        camera.setRotY(rotY);
        camera.setRotZ(rotZ);
    }
}
