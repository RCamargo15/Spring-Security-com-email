package com.rcamargo15.cursospringsecurity.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class RegistrationRequest {

    private final String name;
    private final String email;
    private final String password;
}
