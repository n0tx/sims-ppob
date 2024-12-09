package com.riki.simsppob.controller;

import com.riki.simsppob.model.request.LoginRequest;
import com.riki.simsppob.model.request.RegisterRequest;
import com.riki.simsppob.model.response.LoginResponse;
import com.riki.simsppob.model.response.ApiResponse;
import com.riki.simsppob.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "Membership"
)
public class AuthController {

    @Autowired
    private AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ApiResponse.<String>builder()
                .status(0)
                .message("Registrasi berhasil silahkan login")
                .data(null)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = new LoginResponse();

        try {
             response = authService.login(request);
            return ApiResponse.<LoginResponse>builder()
                    .status(0)
                    .message("Login Sukses")
                    .data(response)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<LoginResponse>builder()
                    .status(0)
                    .message(e.getMessage())
                    .data(response)
                    .build();

        }

    }
}
