package com.remote_vitals.repositories;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DoctorUpdateDto extends UserUpdateDto {
    private String qualificationString;
    private String description;

    public DoctorUpdateDto(
            String phoneNumber,
            Visibility pnVisibility,
            String email,
            Visibility eVisibility,
            String password,
            String qualificationString,
            String description
    ) {
        super(phoneNumber, pnVisibility, email, eVisibility, password);
        this.qualificationString = qualificationString;
        this.description = description;
    }
}
