package com.riki.simsppob.service;

import com.riki.simsppob.model.request.ProfileUpdateRequest;
import com.riki.simsppob.model.response.ProfileResponse;

public interface ProfileService {
    ProfileResponse getProfile(String email);
    ProfileResponse updateProfile(String email, ProfileUpdateRequest request);
    ProfileResponse updateProfileImage(String email, String uri);
}
