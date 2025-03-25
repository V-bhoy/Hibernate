package com.repository;

import com.entity.Person;
import com.entity.Vehicle;

public interface OwnerRepo {
    void insertManualData();
    Person fetchDetails(int ownerId);
}
