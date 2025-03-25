package com.repository;

import com.entity.AdhaarCard;

public interface AdhaarRepo {
   AdhaarCard getDetailsByAdhaarId(int adhaarId);

}
