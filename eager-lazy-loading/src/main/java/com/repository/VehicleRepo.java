package com.repository;

import com.entity.Vehicle;

public interface VehicleRepo {
    Vehicle fetchDetailsByVehicleId(int vehicleId);
}
