package com.api.messengerservice.entities;

import com.api.messengerservice.utils.PackageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table (name = "package")
public class Package {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String packageType;
    @Column
    @NotNull (message = "The weight cannot be null")
    private double weight;
    @Column
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
