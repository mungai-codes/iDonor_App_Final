package net.mungai.idonor.donor.service;

import net.mungai.idonor.donor.repos.AddressRepo;
import net.mungai.idonor.donor.models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo;

    public List<Address> listAll(){
        return addressRepo.findAll();
    }

    public void save(Address address){
        addressRepo.save(address);
    }

    public Address find(Long id){
        return addressRepo.findById(id).get();
    }

    public void delete(Long id){
        addressRepo.deleteById(id);
    }

}
