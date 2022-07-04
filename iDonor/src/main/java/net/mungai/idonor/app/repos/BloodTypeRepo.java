package net.mungai.idonor.app.repos;

import net.mungai.idonor.app.models.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodTypeRepo extends JpaRepository<BloodType, Long> {
}
