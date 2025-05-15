package com.remote_vitals.repositories;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDto {
    private String phoneNumber;
    private Visibility pnVisibility;
    private String email;
    private Visibility eVisibility;
    private String password;

    public UserUpdateDto(
            String phoneNumber,
            Visibility pnVisibility,
            String email,
            Visibility eVisibility,
            String password
    ) {
        this.phoneNumber = phoneNumber;
        this.pnVisibility = pnVisibility;
        this.email = email;
        this.eVisibility = eVisibility;
        this.password = password;
    }
}
