package net.mungai.idonor.donor.service;

import net.mungai.idonor.donor.models.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DonorDetailsImpl implements UserDetailsService {

    @Autowired
    private DonorService donorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Donor donor = donorService.getByEmail(username);

        if(donor == null){
            throw new UsernameNotFoundException("User " + donorService.getByEmail(username) + " not found?");
        }
        return new MyDonorDetails(donor);
    }
}
