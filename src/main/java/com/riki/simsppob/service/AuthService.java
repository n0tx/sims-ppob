package com.riki.simsppob.service;

import com.riki.simsppob.model.request.LoginRequest;
import com.riki.simsppob.model.request.RegisterRequest;
import com.riki.simsppob.model.response.LoginResponse;


public interface AuthService {
    void register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
