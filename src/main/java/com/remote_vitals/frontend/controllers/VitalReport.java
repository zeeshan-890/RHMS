package com.remote_vitals.frontend.controllers;

// imports
import com.remote_vitals.entities.Patient;
import com.remote_vitals.entities.VitalRecord;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// lombok annotations
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

// JPA annotations
@Entity
public class VitalReport {
    /******************** Attributes ********************/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "VitalReportGenerator")
    @TableGenerator(
            name = "VitalReportGenerator",
            table = "id_generator",
            pkColumnName = "table_of_pk",
            valueColumnName = "value",
            pkColumnValue = "vital_report",
            allocationSize = 10
    )
    private Integer id;

    @Column(name = "report_when_made",nullable = false)
    private LocalDateTime reportWhenMade;
    /******************* Relationships *******************/
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "vitalReport",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
            },
            orphanRemoval = true
    )
    List<VitalRecord> vitalRecords = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "patient_id",
            nullable = false
    )
    private Patient patient;

    /******************* Constructors *******************/

    public VitalReport(
            LocalDateTime reportWhenMade,
            List<VitalRecord> vitalRecords,
            Patient patient
    )
    {
        this.reportWhenMade = reportWhenMade;
        this.vitalRecords = vitalRecords;
        this.patient = patient;
    }
}
