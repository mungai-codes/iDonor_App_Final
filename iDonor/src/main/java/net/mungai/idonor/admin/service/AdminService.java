package net.mungai.idonor.admin.service;

import net.mungai.idonor.admin.model.Admin;
import net.mungai.idonor.admin.repos.AdminRepo;
import net.mungai.idonor.app.models.Role;
import net.mungai.idonor.app.repos.RoleRepo;
import net.mungai.idonor.donor.models.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private RoleRepo roleRepo;

    public Admin getByUsername(String username){
        return adminRepo.getAdminByUsername(username);
    }

    public void saveWithDefaultUserRole(Admin admin){
        Role roleAdmin = roleRepo.findByName("admin");
        admin.addRole(roleAdmin);
        adminRepo.save(admin);
    }

    public List<Admin> listAll() {
        return adminRepo.findAll();
    }

    public Admin find(Long id) {
        return adminRepo.findById(id).get();
    }

    public void delete(Long id) {
        adminRepo.deleteById(id);
    }

    public void enableAdmin(Long id){
        adminRepo.enableAdmin(id);
    }

    public void disableAdmin(Long id){
        adminRepo.disableAdmin(id);
    }

}
