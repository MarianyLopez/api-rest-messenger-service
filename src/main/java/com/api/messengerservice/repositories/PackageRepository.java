package com.api.messengerservice.repositories;

import com.api.messengerservice.entities.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package,Long> {

}
