package com.riki.simsppob.service.impl;

import com.riki.simsppob.exception.ResourceNotFoundException;
import com.riki.simsppob.model.User;
import com.riki.simsppob.model.request.ProfileUpdateRequest;
import com.riki.simsppob.model.response.ProfileResponse;
import com.riki.simsppob.repository.UserRepository;
import com.riki.simsppob.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user"));

        return ProfileResponse
                .builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImage(user.getProfileImage())
                .build();
    }

    @Override
    public ProfileResponse updateProfile(String email, ProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);

        return ProfileResponse
                .builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImage(user.getProfileImage())
                .build();
    }

    @Override
    public ProfileResponse updateProfileImage(String email, String uri) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user"));

        user.setProfileImage(uri);
        userRepository.save(user);

        return ProfileResponse
                .builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImage(user.getProfileImage())
                .build();
    }

}
