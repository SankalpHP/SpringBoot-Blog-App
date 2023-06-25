package net.javaguides.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    private Long id;

    @NotEmpty(message = "first name should not be empty")
    private String firstName;

    @NotEmpty(message = "last name should not be empty")
    private String lastName;

    @Email
    @NotEmpty(message = "email should not be empty")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
