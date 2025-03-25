package com.service;

import com.entity.Person;
import com.repository.OwnerRepo;

public class OwnerServiceImpl implements OwnerService {
    private OwnerRepo ownerRepo;

    public OwnerServiceImpl(OwnerRepo ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    @Override
    public void insertManualData() {
        try {
            ownerRepo.insertManualData();
        }catch(Exception e) {
            throw new RuntimeException("Error inserting data!",e);
        }
    }

    // just for better readability and understanding, segregating methods
    @Override
    public Person fetchAllDetailsByOwnerId(int ownerId) {
        return ownerRepo.fetchDetails(ownerId);
    }

    @Override
    public Person fetchOwnerDetailsOnlyByOwnerId(int ownerId) {
        return ownerRepo.fetchDetails(ownerId);
    }

    @Override
    public Person fetchVehicleDetailsOnlyByOwnerId(int ownerId) {
        return ownerRepo.fetchDetails(ownerId);
    }
}
