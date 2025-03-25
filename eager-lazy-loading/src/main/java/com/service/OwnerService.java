package com.service;

import com.entity.Person;

public interface OwnerService {
    void insertManualData();
    Person fetchAllDetailsByOwnerId(int ownerId);
    Person fetchOwnerDetailsOnlyByOwnerId(int ownerId);
    Person fetchVehicleDetailsOnlyByOwnerId(int ownerId);
}
