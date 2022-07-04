package net.mungai.idonor.donor.service;

import net.mungai.idonor.app.models.BloodType;
import net.mungai.idonor.app.models.Role;
import net.mungai.idonor.app.repos.RoleRepo;
import net.mungai.idonor.donor.models.Donor;
import net.mungai.idonor.donor.models.Gender;
import net.mungai.idonor.donor.repos.DonorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Service
public class DonorService{

    @Autowired
    private DonorRepo donorRepo;
    @Autowired
    private RoleRepo roleRepo;

    public List<Donor> listAll(String keyword) {
        if( keyword != null){
            return donorRepo.search(keyword);
        }
        return donorRepo.findAll();
    }

    public List<Donor> addressSearch(String keyword){
        if(keyword != null){
            return donorRepo.searchByAddress(keyword);
        }
        System.err.println("address" + keyword + " not found!?");
        return donorRepo.findAll();
    }

    public void saveWithDefaultUserRole(Donor donor){
        Role donorsRole = roleRepo.findByName("user");
        donor.addRole(donorsRole);
        donorRepo.save(donor);
    }

    public List<Donor> getAllDonors(){
        return donorRepo.findAll();
    }

    public Donor find(Long id) {
        return donorRepo.findById(id).get();
    }

    public void delete(Long id) {
        donorRepo.deleteById(id);
    }

    public Donor getByEmail(String email){
        return donorRepo.getDonorByEmail(email);
    }

    public void enableDonor(Long id){
        donorRepo.enableDonor(id);
    }

    public void disableDonor(Long id){
        donorRepo.disableDonor(id);
    }

    public void setAvailable(Long id){
        donorRepo.setAvailable(id);
    }
    public void setUnAvailable(Long id){
        donorRepo.setUnAvailable(id);
    }

    public String getPhoneNumber(Long id){
       return donorRepo.getPhoneNumberById(id);

    }

    public void updateUser(String firstname, String surname, Date dob, BloodType bloodType,Gender gender,String phonenumber,String password, Long id){
        donorRepo.updateDonor(firstname,surname,dob,bloodType,gender,phonenumber,password,id);
    }

    public void updateUserAddress(String county, String subcounty, String ward, Long id){
        donorRepo.updateDonorAddress(county,subcounty, ward, id);
    }

    public Page<Donor> approvedDonors(int page){
        Pageable pageable = PageRequest.of(page-1,5, Sort.by("id").descending());
        return donorRepo.findDonorByEnabledTrue(pageable);
    }

   public Page<Donor> pendingDonors(int page){
       Pageable pageable = PageRequest.of(page-1,5, Sort.by("id").descending());
        return donorRepo.findDonorByEnabledFalse(pageable);
    }

    public Page<Donor> disabledDonors(int page){
        Pageable pageable = PageRequest.of(page - 1,5, Sort.by("id"));
        return donorRepo.disabledDonors(pageable);
    }

    public Page<Donor> findAll(int page){
        Pageable pageable = PageRequest.of(page - 1,5, Sort.by("enabled"));
        return donorRepo.findAll(pageable);
    }

    public List<Donor> searchWithDonorDetails(String bloodType, String address){

        return donorRepo.searchWithDonorDetails(bloodType,address);
    }

    @Transactional
    @Modifying
    public void deleteDonor(long id){
       donorRepo.deleteById(id);
    }

    public Page<Donor> aTest(int page){
        Pageable pageable = PageRequest.of(page-1,2);
    return donorRepo.findAll(pageable);
    }


}
