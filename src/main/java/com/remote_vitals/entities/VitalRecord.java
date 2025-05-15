/*
 * Author  : Taimoor Ashraf
 * Purpose : VitalRecord Class That is the super class of all types of 
 *           vitals
 */
// ---------------------- // BEGIN // --------------------- //
package com.remote_vitals.entities;

// imports
import com.remote_vitals.repositories.VitalStatus;
import com.remote_vitals.frontend.controllers.VitalReport;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Base class for all types of vital signs and measurements in the system.
 * Uses single table inheritance strategy where all vital types are stored in one table
 * with a discriminator column to distinguish between different types.
 * 
 * Lombok annotations:
 * @Data - Generates getters, setters, toString, equals, and hashCode
 * @NoArgsConstructor - Generates constructor with no parameters
 * @SuperBuilder - Enables builder pattern with inheritance support
 */
@Data
@ToString(exclude = "vitalReport")
@NoArgsConstructor
@SuperBuilder

/**
 * JPA annotations:
 * @Entity - Marks this class as a JPA entity
 * @Inheritance - Specifies SINGLE_TABLE inheritance strategy
 * @DiscriminatorColumn - Defines the column used to distinguish between different vital types
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "vital_type", discriminatorType = DiscriminatorType.STRING)
public class VitalRecord {
    /******************* Attributes *******************/
    /**
     * Primary key for the vital record
     * Uses TABLE strategy for ID generation to ensure uniqueness
     */
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "VitalRecordGenerator")
    @TableGenerator(
            name = "VitalRecordGenerator",
            table = "id_generator",
            pkColumnName = "table_of_pk",
            valueColumnName = "value",
            pkColumnValue = "vital_record",
            allocationSize = 10
    )
    private Integer id;

    /** The measured value of the vital sign */
    private float value;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VitalStatus status;

    /***************** Relationships ******************/
    /**
     * The vital report this record belongs to
     * Many vital records can belong to one vital report
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "vital_report_id",
            nullable = false
    )
    private VitalReport vitalReport;

    /****************** Constructors ******************/
    /**
     * Constructor that takes an ID
     * @param id The ID to set for this vital record
     */
    public VitalRecord(Integer id, VitalStatus status){
        this.id = id;
        this.status = status;
    }
}
// ----------------------- // END // ---------------------- //
