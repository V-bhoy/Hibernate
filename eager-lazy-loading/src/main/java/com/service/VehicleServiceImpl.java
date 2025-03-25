package com.service;

import com.entity.Vehicle;
import com.repository.VehicleRepo;

public class VehicleServiceImpl implements VehicleService {
    private VehicleRepo vehicleRepo;

    public VehicleServiceImpl(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    // just for better readability and understanding, segregating methods

    @Override
    public Vehicle fetchAllDetailsByVehicleId(int vehicleId) {
        return vehicleRepo.fetchDetailsByVehicleId(vehicleId);
    }

    @Override
    public Vehicle fetchOwnerDetailsOnlyByVehicleId(int vehicleId) {
        return vehicleRepo.fetchDetailsByVehicleId(vehicleId);
    }

    @Override
    public Vehicle fetchVehicleDetailsOnlyByVehicleId(int vehicleId) {
        return vehicleRepo.fetchDetailsByVehicleId(vehicleId);
    }
}
