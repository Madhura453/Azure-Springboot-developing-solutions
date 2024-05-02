package com.all.azureservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenClass {
    private String tokenType;

    private String expiresIn;

    private String extExpiresIn;

    private String accessToken;

}
