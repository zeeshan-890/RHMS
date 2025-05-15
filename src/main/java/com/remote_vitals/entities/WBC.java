/*
 * Author  : Taimoor Ashraf
 * Purpose : WBC Class that is a sort of vital record
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
@DiscriminatorValue("WBC")
public class WBC extends VitalRecord {

    /******************** Attributes *******************/
    public static final float MIN_WBC = 4.0f;  // thousand cells per microliter
    public static final float MAX_WBC = 11.0f; // thousand cells per microliter

    public static final boolean IS_VALID_BELOW_MIN_WBC = true;
    public static final boolean IS_VALID_ABOVE_MAX_WBC = true;

    /********************* Setters *********************/
    @Override
    public void setValue(float wbcCount) throws IllegalArgumentException {
        if (!isValid(wbcCount)) {
            throw new IllegalArgumentException(
                    getClass().getSimpleName() + " has an invalid value(" + wbcCount + ")"
            );
        }
        super.setValue(wbcCount);
    }

    /******************* Constructors *******************/
    public WBC(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public WBC(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float wbcCount) {
        return (wbcCount >= MIN_WBC && wbcCount <= MAX_WBC) ||
                (wbcCount < MIN_WBC && IS_VALID_BELOW_MIN_WBC) ||
                (wbcCount > MAX_WBC && IS_VALID_ABOVE_MAX_WBC);
    }

    private static VitalStatus determineStatus(float wbcCount) {
        if (wbcCount >= MIN_WBC && wbcCount <= MAX_WBC) {
            return VitalStatus.NORMAL;
        } else if ((wbcCount < MIN_WBC && IS_VALID_BELOW_MIN_WBC) ||
                (wbcCount > MAX_WBC && IS_VALID_ABOVE_MAX_WBC)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    WBC.class.getSimpleName() + " has an invalid value(" + wbcCount + ")"
            );
        }
    }
}
// ----------------------- //
