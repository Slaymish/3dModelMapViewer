package dev.hamishapps.buildingarchive.servlets;

import dev.hamishapps.buildingarchive.Building;
import dev.hamishapps.buildingarchive.DataPoint;
import dev.hamishapps.buildingarchive.Persistency;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "UploadForm", urlPatterns = "/upload")
@MultipartConfig
public class UploadForm extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // process form
        processForm(request, response);

        response.sendRedirect("explore.html");
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

        // File upload logic
        try {
            Part filePart = request.getPart("modelFile");
            if (filePart != null && filePart.getSize() > 0) {
                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String fileExtension = "";
                int i = originalFileName.lastIndexOf('.');
                if (i > 0) {
                    fileExtension = originalFileName.substring(i);
                }
                String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

                String appPath = request.getServletContext().getRealPath("");
                String uploadFilePath = "uploads" + File.separator + "models"; // Consistent separator usage
                File fileSaveDir = new File(appPath + File.separator + uploadFilePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdirs();
                }

                String filePath = fileSaveDir.getAbsolutePath() + File.separator + uniqueFileName;
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                    // Store a web-accessible path (relative to context root)
                    building.setModelPath("uploads/models/" + uniqueFileName);
                }
            }
        } catch (Exception e) {
            // Log the error or handle it appropriately
            e.printStackTrace();
            // Optionally, set an error status or message for the response
            // response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // return; // Or throw ServletException
        }

        if (building.hasEnoughParams()) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            Persistency.addDataPoint(new DataPoint(building));
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
