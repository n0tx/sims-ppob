package com.riki.simsppob.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.riki.simsppob.exception.BadRequestException;
import com.riki.simsppob.model.request.ProfileUpdateRequest;
import com.riki.simsppob.model.response.ProfileResponse;
import com.riki.simsppob.model.response.ApiResponse;
import com.riki.simsppob.service.ProfileService;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/profile")
@Tag(
        name = "Membership"
)
@SecurityRequirement(name = "Bearer Authentication")
public class ProfileController {

    private final ProfileService profileService;

    @Value("${minio.bucketName}")
    private String minioBucketName;

    @Value("${minio.publicUrl}")
    private String minioPublicUrl;

    @Value("${minio.imagesDirectory}")
    private String minioImagesDirectory;

    private final MinioClient minioClient;

    public ProfileController(ProfileService profileService, MinioClient minioClient) {
        this.profileService = profileService;
        this.minioClient = minioClient;
    }

    // using @AuthenticationPrincipal, Authentication.getPrincipal() to get a user
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public ApiResponse<ProfileResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {

        // using email
        ProfileResponse response = profileService.getProfile(userDetails.getUsername());

        return ApiResponse.<ProfileResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public ApiResponse<ProfileResponse> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ProfileUpdateRequest request) {
        ProfileResponse response = profileService.updateProfile(userDetails.getUsername(), request);

        return ApiResponse.<ProfileResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProfileResponse> updateProfileImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file) {

        if (file == null) {
            throw new BadRequestException("Format Image tidak sesuai");
        }

        try (InputStream inputStream = file.getInputStream()) {

            String contentType = file.getContentType();
            if (contentType != null &&
                    !contentType.equals("image/jpeg") &&
                    !contentType.equals("image/png")) {
                throw new BadRequestException("Format Image tidak sesuai");
            }

            String pathName = String.format(
                    "/%s/%s",
                    this.minioImagesDirectory,
                    file.getOriginalFilename());

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(this.minioBucketName)
                            .object(pathName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());

            String uri = this.minioPublicUrl + "/" + this.minioBucketName + pathName;

            ProfileResponse response = profileService.updateProfileImage(userDetails.getUsername(), uri);

            return ApiResponse.<ProfileResponse>builder()
                    .status(0)
                    .message("Sukses")
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
