package com.remote_vitals.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Check(name = "schedule_check", constraints = "starting_time < ending_time")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ScheduleGenerator")
    @TableGenerator(
            name = "ScheduleGenerator",
            table = "id_generator",
            pkColumnName = "table_of_pk",
            valueColumnName = "value",
            pkColumnValue = "schedule",
            allocationSize = 10
    )
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime startingTime;

    @Column(nullable = false)
    private LocalDateTime endingTime;

    // Relationships
    @OneToOne(
            mappedBy = "schedule",
            cascade = CascadeType.PERSIST
    )
    private Appointment appointment;


    public Schedule(LocalDateTime startingTime, LocalDateTime endingTime, Appointment appointment) {
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.appointment = appointment;
    }
}
