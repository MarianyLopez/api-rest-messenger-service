package com.api.messengerservice.entities;

import com.api.messengerservice.utils.PackageType;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Hidden
@Entity
@Getter
@Setter
@Table (name = "package")
public class Package {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (nullable = false)
    private Long id;
    @Column (nullable = false,length = 7)
    private String packageType;
    @Column (nullable = false)
    @NotNull (message = "The weight cannot be null")
    private double weight;
    @Column (nullable = false)
    @NotNull (message = "The declared value cannot be null")
    private double declaredValue;

    public Package() {
    }

    public Package(double weight, double declaredValue) {
        this.weight = weight;
        if (weight < 2)
            packageType = PackageType.LIGHTWEIGHT.getNameSpanish();
        else if (weight >= 2 && weight <= 5)
            packageType = PackageType.MEDIUM.getNameSpanish();
        else
            packageType = PackageType.LARGE.getNameSpanish();
        this.declaredValue = declaredValue;
    }
}
