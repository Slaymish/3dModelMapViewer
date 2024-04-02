package com.example.map3dmodels.controller;

import com.example.map3dmodels.model.DataPoint;
import com.example.map3dmodels.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/uploadModel", consumes = "multipart/form-data")
    public ResponseEntity<DataPoint> uploadModel(@RequestParam("modelFile") MultipartFile file,
                                                 @RequestParam("author") String author,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("latitude") double latitude,
                                                 @RequestParam("longitude") double longitude) {
        // Validate input parameters
        if (file.isEmpty() || author.isEmpty() || description.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        DataPoint dataPoint = mapService.saveModelAndDataPoint(file, author, description, latitude, longitude);

        if (dataPoint != null) {
            return ResponseEntity.ok(dataPoint);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/datapoints")
    public ResponseEntity<List<DataPoint>> getAllDataPoints() {
        try {
            List<DataPoint> dataPoints = mapService.getAllDataPoints();
            return ResponseEntity.ok(dataPoints);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}