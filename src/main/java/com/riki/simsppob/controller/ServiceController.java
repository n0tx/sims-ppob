package com.riki.simsppob.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.riki.simsppob.model.response.ServiceResponse;
import com.riki.simsppob.model.response.ApiResponse;
import com.riki.simsppob.service.ServicesService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/services")
@Tag(
        name = "Information"
)
@SecurityRequirement(name = "Bearer Authentication")
public class ServiceController {

    @Autowired
    private ServicesService serviceService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public ApiResponse<List<ServiceResponse>> getAllService() {

        List<ServiceResponse> response = serviceService.getAllServices();

        return ApiResponse.<List<ServiceResponse>>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

}
