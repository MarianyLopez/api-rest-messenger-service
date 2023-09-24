package com.api.messengerservice.repositories;
import com.api.messengerservice.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,String> {
    List<Shipment> findByDeliveryStatus( String deliveryStatus);

    List<Shipment> findByClientId(Long id);

}

