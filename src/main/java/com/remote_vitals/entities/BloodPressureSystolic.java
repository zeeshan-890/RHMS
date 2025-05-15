/*
 * Author  : Taimoor Ashraf
 * Purpose : BloodPressureSystolic Class that is a sort of vital record
 */
// ---------------------- // BEGIN // --------------------- //
package com.remote_vitals.entities;

// imports
import com.remote_vitals.repositories.VitalStatus;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Represents a systolic blood pressure measurement.
 * Extends VitalRecord and includes validation for normal blood pressure ranges.
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder

@Entity
@DiscriminatorValue("BloodPressureSystolic")
public class BloodPressureSystolic extends VitalRecord {

    /******************** Attributes *******************/
    public static final int MIN_SYSTOLIC = 90;
    public static final int MAX_SYSTOLIC = 180;

    public static final boolean IS_VALID_BELOW_MIN_SYSTOLIC = true;
    public static final boolean IS_VALID_ABOVE_MAX_SYSTOLIC = true;

    /********************* Setters ********************/
    @Override
    public void setValue(float systolic) throws IllegalArgumentException {
        if (!isValid(systolic)) {
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + " has an invalid value(" + systolic + ")"
            );
        }
        super.setValue(systolic);
    }

    /******************* Constructors *******************/
    public BloodPressureSystolic(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public BloodPressureSystolic(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float systolic) {
        return (systolic >= MIN_SYSTOLIC && systolic <= MAX_SYSTOLIC) ||
                (systolic < MIN_SYSTOLIC && IS_VALID_BELOW_MIN_SYSTOLIC) ||
                (systolic > MAX_SYSTOLIC && IS_VALID_ABOVE_MAX_SYSTOLIC);
    }

    private static VitalStatus determineStatus(float value) {
        if (value >= MIN_SYSTOLIC && value <= MAX_SYSTOLIC) {
            return VitalStatus.NORMAL;
        } else if ((value < MIN_SYSTOLIC && IS_VALID_BELOW_MIN_SYSTOLIC) ||
                (value > MAX_SYSTOLIC && IS_VALID_ABOVE_MAX_SYSTOLIC)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    BloodPressureSystolic.class.getSimpleName() + " has an invalid value(" + value + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
