package com.example.map3dmodels.repository;

import com.example.map3dmodels.model.DataPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataPointRepository extends JpaRepository<DataPoint, Long> {
}
