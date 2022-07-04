package net.mungai.idonor.admin.service;

import net.mungai.idonor.admin.model.Admin;
import net.mungai.idonor.donor.service.MyDonorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminDetailsImpl implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminService.getByUsername(username);

        if(admin == null){
            throw new UsernameNotFoundException("User " + adminService.getByUsername(username) + " not found?");
        }
        return new MyAdminDetails(admin);
    }
}
