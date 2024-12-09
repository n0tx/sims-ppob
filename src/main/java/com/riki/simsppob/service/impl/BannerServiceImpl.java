package com.riki.simsppob.service.impl;

import com.riki.simsppob.model.Banner;
import com.riki.simsppob.model.response.BannerResponse;
import com.riki.simsppob.repository.BannerRepository;
import com.riki.simsppob.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public List<BannerResponse> getAllBanners() {
        Iterable<Banner> banners = this.bannerRepository.findAll();

        List<BannerResponse> responses = new ArrayList<>();
        banners.forEach(
                banner -> responses.add(new BannerResponse(
                        banner.getBannerName(),
                        banner.getBannerImage(),
                        banner.getDescription())));

        return responses;
    }
}
