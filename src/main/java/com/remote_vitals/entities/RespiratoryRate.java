/*
 * Author  : Taimoor Ashraf
 * Purpose : RespiratoryRate Class that is a sort of vital record
 */
// ---------------------- // BEGIN // --------------------- //
package com.remote_vitals.entities;

// imports
import com.remote_vitals.repositories.VitalStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

// lombok annotations
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder

// JPA annotations
@Entity
@DiscriminatorValue("RespiratoryRate")
public class RespiratoryRate extends VitalRecord {

    /******************** Attributes *******************/
    public static final float MIN_RESPIRATORY_RATE = 12.0f; // breaths per minute
    public static final float MAX_RESPIRATORY_RATE = 20.0f; // breaths per minute

    public static final boolean IS_VALID_BELOW_MIN_RESPIRATORY_RATE = true;
    public static final boolean IS_VALID_ABOVE_MAX_RESPIRATORY_RATE = true;

    /********************* Setters *********************/
    @Override
    public void setValue(float respiratoryRate) throws IllegalArgumentException {
        if (!isValid(respiratoryRate)) {
            throw new IllegalArgumentException(
                    getClass().getSimpleName() + " has an invalid value(" + respiratoryRate + ")"
            );
        }
        super.setValue(respiratoryRate);
    }

    /******************* Constructors *******************/
    public RespiratoryRate(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public RespiratoryRate(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float respiratoryRate) {
        return (respiratoryRate >= MIN_RESPIRATORY_RATE && respiratoryRate <= MAX_RESPIRATORY_RATE) ||
                (respiratoryRate < MIN_RESPIRATORY_RATE && IS_VALID_BELOW_MIN_RESPIRATORY_RATE) ||
                (respiratoryRate > MAX_RESPIRATORY_RATE && IS_VALID_ABOVE_MAX_RESPIRATORY_RATE);
    }

    private static VitalStatus determineStatus(float respiratoryRate) {
        if (respiratoryRate >= MIN_RESPIRATORY_RATE && respiratoryRate <= MAX_RESPIRATORY_RATE) {
            return VitalStatus.NORMAL;
        } else if ((respiratoryRate < MIN_RESPIRATORY_RATE && IS_VALID_BELOW_MIN_RESPIRATORY_RATE) ||
                (respiratoryRate > MAX_RESPIRATORY_RATE && IS_VALID_ABOVE_MAX_RESPIRATORY_RATE)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    RespiratoryRate.class.getSimpleName() + " has an invalid value(" + respiratoryRate + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
