package com.ead.authuser.dto;

import com.ead.authuser.services.validation.CpfValid;
import com.ead.authuser.services.validation.UsertValid;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@UsertValid
@Data
public class UserDto {

    public interface UserView {

        interface RegistrationPost {}
        interface UserPut {}
        interface PasswordPut {}
        interface ImagePut {}
    }

    @Indexed(unique = true)
    @JsonView(UserView.RegistrationPost.class)
    @NotBlank(groups = UserView.RegistrationPost.class)
    private String username;
    @Indexed(unique = true)
    @Email(groups = UserView.RegistrationPost.class)
    @NotBlank(groups = UserView.RegistrationPost.class)
    @JsonView({UserView.RegistrationPost.class})
    private String email;
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 6, max = 12)
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;
    @NotBlank(groups = UserView.PasswordPut.class)
    @Size(min = 6, max = 12, groups = UserView.PasswordPut.class)
    @JsonView({UserView.PasswordPut.class})
    private String oldPassword;
    @Size(min = 4, max = 150, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String fullname;
    @Size(max = 20, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;
    @Size(max = 11, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @JsonView({UserView.RegistrationPost.class})
    @CpfValid(groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    private String cpf;
    @NotBlank(groups = UserView.ImagePut.class)
    @JsonView({UserView.ImagePut.class})
    private String imageUrl;

}

