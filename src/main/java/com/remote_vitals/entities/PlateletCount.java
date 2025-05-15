/*
 * Author  : Taimoor Ashraf
 * Purpose : PlateletCount Class that is a sort of vital record
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
@DiscriminatorValue("PlateletCount")
public class PlateletCount extends VitalRecord {

    /******************** Attributes *******************/
    public static final float MIN_PLATELET_COUNT = 150000.0f; // per microliter
    public static final float MAX_PLATELET_COUNT = 450000.0f; // per microliter

    public static final boolean IS_VALID_BELOW_MIN_PLATELET = true;
    public static final boolean IS_VALID_ABOVE_MAX_PLATELET = true;

    /********************* Setters *********************/
    @Override
    public void setValue(float plateletCount) throws IllegalArgumentException {
        if (!isValid(plateletCount)) {
            throw new IllegalArgumentException(
                    getClass().getSimpleName() + " has an invalid value(" + plateletCount + ")"
            );
        }
        super.setValue(plateletCount);
    }

    /******************* Constructors *******************/
    public PlateletCount(Integer id, float value) {
        super(id, determineStatus(value));
        setValue(value);
    }

    public PlateletCount(float value) {
        super(null, determineStatus(value));
        setValue(value);
    }

    /********************* Methods *********************/
    public static boolean isValid(float plateletCount) {
        return (plateletCount >= MIN_PLATELET_COUNT && plateletCount <= MAX_PLATELET_COUNT) ||
                (plateletCount < MIN_PLATELET_COUNT && IS_VALID_BELOW_MIN_PLATELET) ||
                (plateletCount > MAX_PLATELET_COUNT && IS_VALID_ABOVE_MAX_PLATELET);
    }

    private static VitalStatus determineStatus(float plateletCount) {
        if (plateletCount >= MIN_PLATELET_COUNT && plateletCount <= MAX_PLATELET_COUNT) {
            return VitalStatus.NORMAL;
        } else if ((plateletCount < MIN_PLATELET_COUNT && IS_VALID_BELOW_MIN_PLATELET) ||
                (plateletCount > MAX_PLATELET_COUNT && IS_VALID_ABOVE_MAX_PLATELET)) {
            return VitalStatus.ABNORMAL;
        } else {
            throw new IllegalArgumentException(
                    PlateletCount.class.getSimpleName() + " has an invalid value(" + plateletCount + ")"
            );
        }
    }
}
// ----------------------- // END // ---------------------- //
