package com.enigma.wmbapi.services;

import com.enigma.wmbapi.dto.response.JwtClaims;
import com.enigma.wmbapi.entity.UserAccount;


public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
