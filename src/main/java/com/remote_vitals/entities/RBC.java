/*
 * Author  : Taimoor Ashraf
 * Purpose : RBC Class that is a sort of vital record
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
@DiscriminatorValue("RBC")
public class RBC extends VitalRecord {

    /******************** Attributes *******************/
    public static final float MIN_RBC = 4.0f; // million cells per microliter
    public static final float MAX_RBC = 6.0f; // million cells per microliter

    public static final boolean IS_VALID_BELOW_MIN_RBC = true;
    public static final boolean IS_VALID_ABOVE_MAX_RBC = true;

    /********************* Setters *********************/
    @Override
    public void setValue(float rbcCount) throws IllegalArgumentException {
        if (!isValid(rbcCount)) {
            throw new IllegalArgumentException(
                    getClass().getSimpleName() + " has an invalid value(" + rbcCount + ")"
            );
        }
        super.setValue(rbcCount);
    }

    /******************* Constructors *******************/
    public RBC(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public RBC(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float rbcCount) {
        return (rbcCount >= MIN_RBC && rbcCount <= MAX_RBC) ||
                (rbcCount < MIN_RBC && IS_VALID_BELOW_MIN_RBC) ||
                (rbcCount > MAX_RBC && IS_VALID_ABOVE_MAX_RBC);
    }

    private static VitalStatus determineStatus(float rbcCount) {
        if (rbcCount >= MIN_RBC && rbcCount <= MAX_RBC) {
            return VitalStatus.NORMAL;
        } else if ((rbcCount < MIN_RBC && IS_VALID_BELOW_MIN_RBC) ||
                (rbcCount > MAX_RBC && IS_VALID_ABOVE_MAX_RBC)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    RBC.class.getSimpleName() + " has an invalid value(" + rbcCount + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
