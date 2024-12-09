package com.riki.simsppob.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.riki.simsppob.model.response.BannerResponse;
import com.riki.simsppob.model.response.ApiResponse;
import com.riki.simsppob.service.BannerService;

@RestController
@RequestMapping("/banner")
@Tag(
        name = "Information"
)
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public ApiResponse<List<BannerResponse>> getAllBanner() {

        List<BannerResponse> response = bannerService.getAllBanners();

        return ApiResponse.<List<BannerResponse>>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

}
