package com.example.medcheckb8.db.service;

import com.example.medcheckb8.db.dto.request.AuthenticationRequest;
import com.example.medcheckb8.db.dto.request.RegisterRequest;
import com.example.medcheckb8.db.dto.response.AuthenticationResponse;
import com.google.firebase.auth.FirebaseAuthException;

public interface AccountService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;

}
