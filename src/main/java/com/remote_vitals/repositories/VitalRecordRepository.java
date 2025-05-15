package com.remote_vitals.repositories;

import com.remote_vitals.entities.VitalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VitalRecordRepository extends JpaRepository<VitalRecord, Integer> {
}
