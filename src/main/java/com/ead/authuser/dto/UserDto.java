package com.ead.authuser.dto;

import com.ead.authuser.services.validation.UsertValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@UsertValid
@Data
public class UserDto {
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 12)
    private String password;
    private String oldPassword;
    @NotBlank
    @Size(min = 4, max = 150)
    private String fullname;
    @Size(max = 20)
    private String phoneNumber;
    @Size(max = 11)
    private String cpf;
    private String imageUrl;


}
