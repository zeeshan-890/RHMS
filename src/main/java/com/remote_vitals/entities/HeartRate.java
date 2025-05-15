/*
 * Author  : Taimoor Ashraf
 * Purpose : HeartRate Class that is a sort of vital record
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
@DiscriminatorValue("HeartRate")
public class HeartRate extends VitalRecord {

    /******************** Attributes *******************/
    public static final int MIN_HEART_RATE = 60;   // beats per minute
    public static final int MAX_HEART_RATE = 100;  // beats per minute

    public static final boolean IS_VALID_BELOW_MIN_HEART_RATE = true;
    public static final boolean IS_VALID_ABOVE_MAX_HEART_RATE = true;

    /********************* Setters ********************/
    @Override
    public void setValue(float heartRate) throws IllegalArgumentException {
        if (!isValid(heartRate)) {
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + " has an invalid value(" + heartRate + ")"
            );
        }
        super.setValue(heartRate);
    }

    /******************* Constructors *******************/
    public HeartRate(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public HeartRate(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float heartRate) {
        return (heartRate >= MIN_HEART_RATE && heartRate <= MAX_HEART_RATE) ||
                (heartRate < MIN_HEART_RATE && IS_VALID_BELOW_MIN_HEART_RATE) ||
                (heartRate > MAX_HEART_RATE && IS_VALID_ABOVE_MAX_HEART_RATE);
    }

    private static VitalStatus determineStatus(float heartRate) {
        if (heartRate >= MIN_HEART_RATE && heartRate <= MAX_HEART_RATE) {
            return VitalStatus.NORMAL;
        } else if ((heartRate < MIN_HEART_RATE && IS_VALID_BELOW_MIN_HEART_RATE) ||
                (heartRate > MAX_HEART_RATE && IS_VALID_ABOVE_MAX_HEART_RATE)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    HeartRate.class.getSimpleName() + " has an invalid value(" + heartRate + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
