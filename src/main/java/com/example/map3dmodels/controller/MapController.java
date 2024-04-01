package com.example.map3dmodels.controller;// src/main/java/com/example/map3dmodels/controller/MapController.java

import com.example.map3dmodels.model.DataPoint;
import com.example.map3dmodels.service.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.map3dmodels.service.MapService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class MapController {

    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @PostMapping("/uploadModel")
    public ResponseEntity<?> uploadModel(@RequestParam("modelFile") MultipartFile file,
                                         @RequestParam("author") String author,
                                         @RequestParam("description") String description,
                                         @RequestParam("latitude") double latitude,
                                         @RequestParam("longitude") double longitude) {
        DataPoint dataPoint = mapService.saveModelAndDataPoint(file, author, description, latitude, longitude);

        if (dataPoint != null) {
            return ResponseEntity.ok(dataPoint);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload model");
        }
    }

    @GetMapping("/datapoints")
    public ResponseEntity<List<DataPoint>> getAllDataPoints() {
        List<DataPoint> dataPoints = mapService.getAllDataPoints(); // Make sure this service method is implemented
        return ResponseEntity.ok(dataPoints);
    }
}
