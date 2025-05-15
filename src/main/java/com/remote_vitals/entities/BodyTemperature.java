/*
 * Author  : Taimoor Ashraf
 * Purpose : BodyTemperature Class that is a sort of vital record
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
@DiscriminatorValue("BodyTemperature")
public class BodyTemperature extends VitalRecord {

    /******************** Attributes *******************/
    public static final float MIN_TEMP = 30.0f; // Hypothermia threshold
    public static final float MAX_TEMP = 42.0f; // Hyperthermia threshold

    public static final boolean IS_VALID_BELOW_MIN_TEMP = true;
    public static final boolean IS_VALID_ABOVE_MAX_TEMP = true;

    /********************* Setters ********************/
    @Override
    public void setValue(float temperature) throws IllegalArgumentException {
        if (!isValid(temperature)) {
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + " has an invalid value(" + temperature + ")"
            );
        }
        super.setValue(temperature);
    }

    /******************* Constructors *******************/
    public BodyTemperature(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public BodyTemperature(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float temperature) {
        return (temperature >= MIN_TEMP && temperature <= MAX_TEMP) ||
                (temperature < MIN_TEMP && IS_VALID_BELOW_MIN_TEMP) ||
                (temperature > MAX_TEMP && IS_VALID_ABOVE_MAX_TEMP);
    }

    private static VitalStatus determineStatus(float temperature) {
        if (temperature >= MIN_TEMP && temperature <= MAX_TEMP) {
            return VitalStatus.NORMAL;
        } else if ((temperature < MIN_TEMP && IS_VALID_BELOW_MIN_TEMP) ||
                (temperature > MAX_TEMP && IS_VALID_ABOVE_MAX_TEMP)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    BodyTemperature.class.getSimpleName() + " has an invalid value(" + temperature + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
