package com.service;

import com.entity.AdhaarCard;
import com.repository.AdhaarRepo;

public class AdhaarServiceImpl implements AdhaarService {
    private AdhaarRepo adhaarRepo;

    public AdhaarServiceImpl(AdhaarRepo adhaarRepo) {
        this.adhaarRepo = adhaarRepo;
    }

    @Override
    public AdhaarCard getDetailsByAdhaarId(int adhaarId) {
        return adhaarRepo.getDetailsByAdhaarId(adhaarId);
    }
}
