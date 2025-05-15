/*
 * Author  : Taimoor Ashraf
 * Purpose : Haemoglobin Class that is a sort of vital record
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

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder

@Entity
@DiscriminatorValue("Haemoglobin")
public class Haemoglobin extends VitalRecord {

    /******************** Attributes *******************/
    public static final float MIN_HEMOGLOBIN = 12.0f;  // g/dL
    public static final float MAX_HEMOGLOBIN = 18.0f;  // g/dL

    public static final boolean IS_VALID_BELOW_MIN_HEMOGLOBIN = true;
    public static final boolean IS_VALID_ABOVE_MAX_HEMOGLOBIN = true;

    /********************* Setters ********************/
    @Override
    public void setValue(float haemoglobin) throws IllegalArgumentException {
        if (!isValid(haemoglobin)) {
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + " has an invalid value(" + haemoglobin + ")"
            );
        }
        super.setValue(haemoglobin);
    }

    /******************* Constructors *******************/
    public Haemoglobin(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public Haemoglobin(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float haemoglobin) {
        return (haemoglobin >= MIN_HEMOGLOBIN && haemoglobin <= MAX_HEMOGLOBIN) ||
                (haemoglobin < MIN_HEMOGLOBIN && IS_VALID_BELOW_MIN_HEMOGLOBIN) ||
                (haemoglobin > MAX_HEMOGLOBIN && IS_VALID_ABOVE_MAX_HEMOGLOBIN);
    }

    private static VitalStatus determineStatus(float haemoglobin) {
        if (haemoglobin >= MIN_HEMOGLOBIN && haemoglobin <= MAX_HEMOGLOBIN) {
            return VitalStatus.NORMAL;
        } else if ((haemoglobin < MIN_HEMOGLOBIN && IS_VALID_BELOW_MIN_HEMOGLOBIN) ||
                (haemoglobin > MAX_HEMOGLOBIN && IS_VALID_ABOVE_MAX_HEMOGLOBIN)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    Haemoglobin.class.getSimpleName() + " has an invalid value(" + haemoglobin + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
