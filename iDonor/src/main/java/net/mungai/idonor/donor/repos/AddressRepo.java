package net.mungai.idonor.donor.repos;

import net.mungai.idonor.donor.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

}
