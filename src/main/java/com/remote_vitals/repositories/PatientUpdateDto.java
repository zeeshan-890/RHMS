package com.remote_vitals.repositories;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PatientUpdateDto extends UserUpdateDto {
    private String medicalHistory;
    private String nextOfKinEmail;

    public PatientUpdateDto(
            String phoneNumber,
            Visibility pnVisibility,
            String email,
            Visibility eVisibility,
            String password,
            String medicalHistory,
            String nextOfKinEmail
    ) {
        super(phoneNumber, pnVisibility, email, eVisibility, password);
        this.medicalHistory = medicalHistory;
        this.nextOfKinEmail = nextOfKinEmail;
    }
}
