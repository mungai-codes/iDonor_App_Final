package net.mungai.idonor.app.models;

import javax.persistence.*;

@Entity
@Table(name = "blood_types")
public class BloodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String name;

    public BloodType() {
    }

    public BloodType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BloodType(Long id) {
        this.id = id;
    }

    public BloodType(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
