package com.riki.simsppob.service.impl;

import com.riki.simsppob.model.response.ServiceResponse;
import com.riki.simsppob.repository.ServiceRepository;
import com.riki.simsppob.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicesServiceImpl implements ServicesService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceResponse> getAllServices() {
        Iterable<com.riki.simsppob.model.Service> services = this.serviceRepository.findAll();

        List<ServiceResponse> responses = new ArrayList<>();
        services.forEach(
                service -> responses.add(new ServiceResponse(
                        service.getServiceCode(),
                        service.getServiceName(),
                        service.getServiceImage(),
                        service.getServiceTariff())));

        return responses;
    }
}
