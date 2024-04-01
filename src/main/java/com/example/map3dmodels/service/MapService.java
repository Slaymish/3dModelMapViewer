package com.example.map3dmodels.service;

import com.example.map3dmodels.model.DataPoint;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface MapService {
    DataPoint saveModelAndDataPoint(MultipartFile file, String author, String description, double latitude, double longitude);

    List<DataPoint> getAllDataPoints();
}
