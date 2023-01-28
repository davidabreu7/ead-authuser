package com.ead.authuser.models;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@QueryEntity
public class UserModel  {

    @Id
    private String id;
    @Indexed(unique = true)
    @NotBlank(groups = UserDto.UserView.RegistrationPost.class)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Username must be alphanumeric")
    private String username;
    @Indexed(unique = true)
    @Email
    private String email;
    @NotBlank
    @JsonIgnore
    private String password;
    @NotBlank
    private String fullname;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;
    @NotNull
    private UserStatus userStatus;
    @NotNull
    private UserType userType;

    public UserModel(UserDto userDto) {
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.fullname = userDto.getFullname();
        this.phoneNumber = userDto.getPhoneNumber();
        this.cpf = userDto.getCpf();
        this.imageUrl = userDto.getImageUrl();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.userStatus = UserStatus.ACTIVE;
        this.userType = UserType.STUDENT;
    }

}


