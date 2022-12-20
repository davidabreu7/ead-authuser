package com.ead.authuser.models;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class UserModel extends RepresentationModel<UserModel> {

    @Id
    private String id;
    @Indexed(unique = true)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
    @NotBlank
    private UserStatus userStatus;
    @NotBlank
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


