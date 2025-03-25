package com.service;

import com.entity.AdhaarCard;
import com.entity.Person;

public interface AdhaarService {
    AdhaarCard getDetailsByAdhaarId(int adhaarId);
}
