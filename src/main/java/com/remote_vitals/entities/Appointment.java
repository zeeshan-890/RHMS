package com.remote_vitals.entities;

import com.remote_vitals.repositories.AppointmentStatus;
//import com.remote_vitals.backend.chat.entities.ChatRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Appointment {
    /******************** Attributes ********************/
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AppointmentGenerator")
    @TableGenerator(
            name = "AppointmentGenerator",
            table = "id_generator",
            pkColumnName = "table_of_pk",
            valueColumnName = "value",
            pkColumnValue = "appointment",
            allocationSize = 10
    )
    private Integer id;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "link_for_room")
    private String linkForRoom;
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

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade ={
                CascadeType.PERSIST,
                CascadeType.REMOVE
            },
            orphanRemoval = true
    )
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

//    @OneToOne(mappedBy = "appointment")
//    private ChatRoom chatRoom;

    /******************** Constructors ********************/
    public Appointment(AppointmentStatus status, Patient patient, Doctor doctor, Schedule schedule/*, ChatRoom chatRoom */) {
        this.status = status;
        this.patient = patient;
        this.doctor = doctor;
        this.schedule = schedule;
//        this.chatRoom = chatRoom;
    }


    public Appointment(Patient patient, Doctor doctor, Schedule schedule /*, ChatRoom chatRoom */) {
        this.status=AppointmentStatus.REQUESTED;
        this.patient = patient;
        this.doctor = doctor;
        this.schedule = schedule;
//        this.chatRoom = chatRoom;
    }

    public Appointment(AppointmentStatus status, String linkForRoom, Patient patient, Doctor doctor, Schedule schedule) {
        this.status = status;
        this.linkForRoom = linkForRoom;
        this.patient = patient;
        this.doctor = doctor;
        this.schedule = schedule;
    }
}

