/*
 * Author  : Taimoor Ashraf
 * Purpose : Height Class that is a sort of vital record
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
@DiscriminatorValue("Height")
public class Height extends VitalRecord {

    /******************** Attributes *******************/
    public static final float MIN_HEIGHT = 0.0f;   // in cm (example: 30cm = 0.3m)
    public static final float MAX_HEIGHT = 272.0f;  // in cm (Robert Wadlow)

    public static final boolean IS_VALID_BELOW_MIN_HEIGHT = false;
    public static final boolean IS_VALID_ABOVE_MAX_HEIGHT = true;

    /********************* Setters *********************/
    @Override
    public void setValue(float height) throws IllegalArgumentException {
        if (!isValid(height)) {
            throw new IllegalArgumentException(
                    this.getClass().getSimpleName() + " has an invalid value(" + height + ")"
            );
        }
        super.setValue(height);
    }

    /******************* Constructors *******************/
    public Height(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public Height(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float height) {
        return (height >= MIN_HEIGHT && height <= MAX_HEIGHT) ||
                (height < MIN_HEIGHT && IS_VALID_BELOW_MIN_HEIGHT) ||
                (height > MAX_HEIGHT && IS_VALID_ABOVE_MAX_HEIGHT);
    }

    private static VitalStatus determineStatus(float height) {
        if (height >= MIN_HEIGHT && height <= MAX_HEIGHT) {
            return VitalStatus.NORMAL;
        } else if ((height < MIN_HEIGHT && IS_VALID_BELOW_MIN_HEIGHT) ||
                (height > MAX_HEIGHT && IS_VALID_ABOVE_MAX_HEIGHT)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    Height.class.getSimpleName() + " has an invalid value(" + height + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
