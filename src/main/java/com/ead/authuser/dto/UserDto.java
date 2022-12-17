package com.ead.authuser.dto;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
public class UserDto {
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
}
