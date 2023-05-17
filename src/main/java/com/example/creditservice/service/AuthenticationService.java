package com.example.creditservice.service;

import com.example.creditservice.model.request.AuthenticationRequest;
import com.example.creditservice.model.request.RegisterRequest;
import com.example.creditservice.model.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
