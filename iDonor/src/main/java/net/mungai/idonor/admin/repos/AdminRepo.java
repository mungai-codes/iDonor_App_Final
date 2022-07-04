package net.mungai.idonor.admin.repos;

import net.mungai.idonor.admin.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {

    @Query("select u from Admin u where u.username = :username")
    public Admin getAdminByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE Admin u set u.enabled = true where u.id =:id")
    void enableAdmin(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Admin u set u.enabled = false where u.id =:id")
    void disableAdmin(@Param("id") Long id);

}
