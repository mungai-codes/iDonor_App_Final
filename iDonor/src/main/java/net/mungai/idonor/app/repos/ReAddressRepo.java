package net.mungai.idonor.app.repos;

import net.mungai.idonor.app.models.RecipientAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReAddressRepo extends JpaRepository<RecipientAddress, Long> {
}
