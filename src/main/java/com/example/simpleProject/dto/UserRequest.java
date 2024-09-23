package com.example.simpleProject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {
    private String name;
    private String email;

    public UserRequest() {
    }

    public UserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
