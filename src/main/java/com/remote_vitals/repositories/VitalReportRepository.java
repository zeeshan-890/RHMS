package com.remote_vitals.repositories;

import com.remote_vitals.frontend.controllers.VitalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VitalReportRepository extends JpaRepository<VitalReport, Integer> {
}
