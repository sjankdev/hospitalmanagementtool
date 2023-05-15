package com.demo.hospitalmanagementtool.security.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoResponse {

    private Long id;
    private String username;
    private String email;
    private List<String> roles;

}
