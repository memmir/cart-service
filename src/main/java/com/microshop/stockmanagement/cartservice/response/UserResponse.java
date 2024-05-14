package com.microshop.stockmanagement.cartservice.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {

    private long userId;
    private String userName;
    private String userSurname;
    private String userPassword;
    private String userEmail;
    private String userAddress;
    private String userPhoneNumber;
    private Long userCreatedDate;
    private Long userUpdatedDate;
}
