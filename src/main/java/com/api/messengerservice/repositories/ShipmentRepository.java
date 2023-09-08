package com.api.messengerservice.repositories;
import com.api.messengerservice.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,String> {

}

