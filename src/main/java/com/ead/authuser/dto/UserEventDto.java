package com.ead.authuser.dto;

import com.ead.authuser.models.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEventDto {

   private String id;
    private String username;
    private String email;
    private String fullname;
    private String userStatus;
    private String userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private String actionType;

    public UserEventDto(UserModel userModel) {
        this.id = userModel.getId();
        this.username = userModel.getUsername();
        this.email = userModel.getEmail();
        this.fullname = userModel.getFullname();
        this.userStatus = userModel.getUserStatus().toString();
        this.userType = userModel.getUserType().toString();
        this.phoneNumber = userModel.getPhoneNumber();
        this.cpf = userModel.getCpf();
        this.imageUrl = userModel.getImageUrl();
    }
}
