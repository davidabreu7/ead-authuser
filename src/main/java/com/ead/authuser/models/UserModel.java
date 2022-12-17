package com.ead.authuser.models;

import com.ead.authuser.dto.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    @Email
    private String email;
    @NotBlank
    @Max(255)
    @JsonIgnore
    private String password;
    @NotBlank
    @Max(150)
    @JsonProperty("full_name")
    private String fullname;
    @Max(20)
    @JsonProperty("phone_number")
    private String phoneNumber;
    @Max(20)
    private String cpf;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    @NotBlank
    @JsonProperty("user_status")
    private UserStatus userStatus;
    @NotBlank
    @JsonProperty("user_type")
    private UserType userTyoe;

    public UserModel(UserDto userModel) {
        this.id = userModel.getId();
        this.username = userModel.getUsername();
        this.email = userModel.getEmail();
        this.password = userModel.getPassword();
        this.fullname = userModel.getFullname();
        this.phoneNumber = userModel.getPhoneNumber();
        this.cpf = userModel.getCpf();
        this.imageUrl = userModel.getImageUrl();
        this.createdAt = userModel.getCreatedAt();
        this.updatedAt = userModel.getUpdatedAt();
        this.userStatus = userModel.getUserStatus();
        this.userTyoe = userModel.getUserTyoe();
    }

}

