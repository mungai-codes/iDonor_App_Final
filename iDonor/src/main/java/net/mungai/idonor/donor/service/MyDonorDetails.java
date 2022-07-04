package net.mungai.idonor.donor.service;

import net.mungai.idonor.app.models.BloodType;
import net.mungai.idonor.app.models.Role;
import net.mungai.idonor.donor.models.Address;
import net.mungai.idonor.donor.models.Donor;
import net.mungai.idonor.donor.models.Gender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyDonorDetails implements UserDetails {

    private final Donor donor;

    public MyDonorDetails(Donor donor) {
        this.donor = donor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<Role> roles = donor.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return donor.getPassword();
    }

    @Override
    public String getUsername() {
        return donor.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return donor.isEnabled();
    }

    public BloodType seeBloodType(){
        return donor.getBloodType();
    }

    public Address seeAddress(){
        return donor.getAddress();
    }

    public Long seeId(){
        return donor.getId();
    }

    public Boolean getStatus(){
        return donor.isEnabled();
    }

//    public Boolean getAvailability(){
//        return donor.isAvailable();
//    }

    public String seeUsername(){
        return donor.getEmail();
    }

    public String seePhoneNumber(){
        return donor.getPhoneNumber();
    }

    public Gender seeGender(){
        return donor.getGender();
    }

    public Date seeDob(){
        return donor.getDateOfBirth();
    }

    public void setFirstName(String firstname){
        this.donor.setFirstName(firstname);
    }
    public void setSurName(String surname){
        this.donor.setSurname(surname);
    }

    public void setGender(Gender gender){
        this.donor.setGender(gender);
    }

    public void setDob(Date dob){
        this.donor.setDateOfBirth(dob);
    }

    public void setBloodType(BloodType bloodType){
        this.donor.setBloodType(bloodType);
    }

    public void setPhoneNumber(String phoneNumber){
        this.donor.setPhoneNumber(phoneNumber);
    }

    public void setPassword(String password){
        this.donor.setPassword(password);
    }

    public void setAddress(Address address){
        this.donor.setAddress(address);
    }

    public Set<Role> hasRole(){
        return donor.getRoles();
    }
}
