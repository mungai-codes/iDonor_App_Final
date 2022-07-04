package net.mungai.idonor.app.service;

import net.mungai.idonor.app.repos.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    public void deleteRoleById(Long id){
        roleRepo.deleteById(id);
    }
}
