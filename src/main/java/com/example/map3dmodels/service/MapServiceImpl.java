// src/main/java/com/example/map3dmodels/service/MapServiceImpl.java

package com.example.map3dmodels.service;

import com.example.map3dmodels.model.Building;
import com.example.map3dmodels.model.DataPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.map3dmodels.repository.BuildingRepository;
import com.example.map3dmodels.repository.DataPointRepository;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    private final BuildingRepository buildingRepository;
    private final DataPointRepository dataPointRepository;

    @Autowired
    private FileUpload fileUpload;

    @Autowired
    public MapServiceImpl(BuildingRepository buildingRepository, DataPointRepository dataPointRepository) {
        this.buildingRepository = buildingRepository;
        this.dataPointRepository = dataPointRepository;
    }

    @Override
    public DataPoint saveModelAndDataPoint(MultipartFile file, String author, String description, double latitude, double longitude) {
        // Here, implement the logic to handle the file storage (e.g., saving it to the filesystem or cloud storage)
        // and create and save a Building and DataPoint object. This is a simplified version.

        Building building = new Building();
        building.setAuthor(author);
        building.setDescription(description);
        // Assume modelPath is where you've stored the model file
        fileUpload.setModelFile(file, building);

        building = buildingRepository.save(building);

        DataPoint dataPoint = new DataPoint();
        dataPoint.setLatitude(latitude);
        dataPoint.setLongitude(longitude);
        dataPoint.setBuilding(building);
        return dataPointRepository.save(dataPoint);
    }

    @Override
    public List<DataPoint> getAllDataPoints() {
        return dataPointRepository.findAll();
    }
}
