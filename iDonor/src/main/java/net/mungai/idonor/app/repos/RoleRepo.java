package net.mungai.idonor.app.repos;

import net.mungai.idonor.app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RoleRepo extends JpaRepository<Role, Long> {

    @Query("Select r from Role r where r.name =?1")
    Role findByName(String name);
}
