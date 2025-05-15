/*
 * Author  : Taimoor Ashraf
 * Purpose : Weight Class that is a sort of vital record
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
@DiscriminatorValue("Weight")
public class Weight extends VitalRecord {

    /******************** Attributes *******************/
    public static final float MIN_WEIGHT = 0.0f;   // minimum weight in kg
    public static final float MAX_WEIGHT = 500.0f; // maximum weight in kg

    public static final boolean IS_VALID_BELOW_MIN_WEIGHT = false;
    public static final boolean IS_VALID_ABOVE_MAX_WEIGHT = true;

    /********************* Setters *********************/
    @Override
    public void setValue(float weight) throws IllegalArgumentException {
        if (!isValid(weight)) {
            throw new IllegalArgumentException(
                    getClass().getSimpleName() + " has an invalid value(" + weight + ")"
            );
        }
        super.setValue(weight);
    }

    /******************* Constructors *******************/
    public Weight(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public Weight(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float weight) {
        return (weight >= MIN_WEIGHT && weight <= MAX_WEIGHT) ||
                (weight < MIN_WEIGHT && IS_VALID_BELOW_MIN_WEIGHT) ||
                (weight > MAX_WEIGHT && IS_VALID_ABOVE_MAX_WEIGHT);
    }

    private static VitalStatus determineStatus(float weight) {
        if (weight >= MIN_WEIGHT && weight <= MAX_WEIGHT) {
            return VitalStatus.NORMAL;
        } else if ((weight < MIN_WEIGHT && IS_VALID_BELOW_MIN_WEIGHT) ||
                (weight > MAX_WEIGHT && IS_VALID_ABOVE_MAX_WEIGHT)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    Weight.class.getSimpleName() + " has an invalid value(" + weight + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
