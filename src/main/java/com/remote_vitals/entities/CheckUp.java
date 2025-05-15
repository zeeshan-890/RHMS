package com.remote_vitals.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class CheckUp {
    /******************** Attributes ********************/
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CheckupGenerator")
    @TableGenerator(
            name = "CheckupGenerator",
            table = "id_generator",
            pkColumnName = "table_of_pk",
            valueColumnName = "value",
            pkColumnValue = "checkup",
            allocationSize = 10
    )
    private Integer id;

    @Column(columnDefinition = "text")
    private String prescription;

    @Column(columnDefinition = "text")
    private String feedback;

    @Column(nullable = false)
    private LocalDateTime timeWhenMade;

    /******************** Relationships ********************/
    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "patient_id",
            nullable = false
    )
    private Patient patient;
    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "doctor_id",
            nullable = false
    )
    private Doctor doctor;

    /******************** Constructors ********************/
    public CheckUp(
            String prescription,
            String feedback,
            LocalDateTime timeWhenMade,
            Patient patient,
            Doctor doctor
    ){
        this.prescription = prescription;
        this.feedback = feedback;
        this.timeWhenMade = timeWhenMade;
        this.patient = patient;
        this.doctor = doctor;
    }
}
