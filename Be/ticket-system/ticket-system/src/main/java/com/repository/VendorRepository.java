// VendorRepository.java
package com.repository;  // Should match your project structure

import com.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findByVendorId(String vendorId);
}