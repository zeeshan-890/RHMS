/*
 * Author  : Taimoor Ashraf
 * Purpose : BloodVolume Class that is a sort of vital record
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
@DiscriminatorValue("BloodVolume")
public class BloodVolume extends VitalRecord {

    /******************** Attributes *******************/
    public static final float MIN_BLOOD_VOLUME = 200.0f;   // in mL
    public static final float MAX_BLOOD_VOLUME = 7000.0f;  // in mL

    public static final boolean IS_VALID_BELOW_MIN_BLOOD_VOLUME = false;
    public static final boolean IS_VALID_ABOVE_MAX_BLOOD_VOLUME = true;

    /********************* Setters ********************/
    @Override
    public void setValue(float volume) throws IllegalArgumentException {
        if (!isValid(volume)) {
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + " has an invalid value(" + volume + ")"
            );
        }
        super.setValue(volume);
    }

    /******************* Constructors *******************/
    public BloodVolume(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public BloodVolume(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float volume) {
        return (volume >= MIN_BLOOD_VOLUME && volume <= MAX_BLOOD_VOLUME) ||
                (volume < MIN_BLOOD_VOLUME && IS_VALID_BELOW_MIN_BLOOD_VOLUME) ||
                (volume > MAX_BLOOD_VOLUME && IS_VALID_ABOVE_MAX_BLOOD_VOLUME);
    }

    private static VitalStatus determineStatus(float volume) {
        if (volume >= MIN_BLOOD_VOLUME && volume <= MAX_BLOOD_VOLUME) {
            return VitalStatus.NORMAL;
        } else if ((volume < MIN_BLOOD_VOLUME && IS_VALID_BELOW_MIN_BLOOD_VOLUME) ||
                (volume > MAX_BLOOD_VOLUME && IS_VALID_ABOVE_MAX_BLOOD_VOLUME)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    BloodVolume.class.getSimpleName() + " has an invalid value(" + volume + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
