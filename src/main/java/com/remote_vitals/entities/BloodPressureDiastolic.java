/*
 * Author  : Taimoor Ashraf
 * Purpose : BloodPressureDiastolic Class that is a sort of vital record
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
@DiscriminatorValue("BloodPressureDiastolic")
public class BloodPressureDiastolic extends VitalRecord {

    /******************** Attributes *******************/
    public static final int MIN_DIASTOLIC = 60;  // Normal lower bound
    public static final int MAX_DIASTOLIC = 120; // Normal upper bound

    public static final boolean IS_VALID_BELOW_MIN_DIASTOLIC = true;
    public static final boolean IS_VALID_ABOVE_MAX_DIASTOLIC = true;

    /********************* Setters ********************/
    @Override
    public void setValue(float diastolic) throws IllegalArgumentException {
        if (!isValid(diastolic)) {
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + " has an invalid value(" + diastolic + ")"
            );
        }
        super.setValue(diastolic);
    }

    /******************* Constructors *******************/
    public BloodPressureDiastolic(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public BloodPressureDiastolic(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float diastolic) {
        return (diastolic >= MIN_DIASTOLIC && diastolic <= MAX_DIASTOLIC) ||
                (diastolic < MIN_DIASTOLIC && IS_VALID_BELOW_MIN_DIASTOLIC) ||
                (diastolic > MAX_DIASTOLIC && IS_VALID_ABOVE_MAX_DIASTOLIC);
    }

    private static VitalStatus determineStatus(float value) {
        if (value >= MIN_DIASTOLIC && value <= MAX_DIASTOLIC) {
            return VitalStatus.NORMAL;
        } else if ((value < MIN_DIASTOLIC && IS_VALID_BELOW_MIN_DIASTOLIC) ||
                (value > MAX_DIASTOLIC && IS_VALID_ABOVE_MAX_DIASTOLIC)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    BloodPressureDiastolic.class.getSimpleName() + " has an invalid value(" + value + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
