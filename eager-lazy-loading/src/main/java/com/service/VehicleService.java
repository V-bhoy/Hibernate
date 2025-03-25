package com.service;

import com.entity.Person;
import com.entity.Vehicle;

public interface VehicleService {
    Vehicle fetchAllDetailsByVehicleId(int vehicleId);
    Vehicle fetchOwnerDetailsOnlyByVehicleId(int vehicleId);
    Vehicle fetchVehicleDetailsOnlyByVehicleId(int vehicleId);
}
