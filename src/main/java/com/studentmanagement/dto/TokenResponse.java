package com.studentmanagement.dto;

public class TokenResponse {
    private String email;
    private String token;

    public TokenResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }


    public String getToken() {
        return token;
    }
}
