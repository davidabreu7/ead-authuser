package com.ead.authuser.dto;

import com.ead.authuser.models.UserModel;

import java.time.LocalDateTime;

public record UserRecord(String id, String username, String email, String fullname,
                         String phoneNumber, String cpf, LocalDateTime createdAt, LocalDateTime updatedAt,
                         String userStatus, String userType) {
    public UserRecord (UserModel userModel) {
        this(userModel.getId(), userModel.getUsername(), userModel.getEmail(), userModel.getFullname(),
                userModel.getPhoneNumber(), userModel.getCpf(), userModel.getCreatedAt(), userModel.getUpdatedAt(),
                userModel.getUserStatus().toString(), userModel.getUserType().toString());
    }

}

