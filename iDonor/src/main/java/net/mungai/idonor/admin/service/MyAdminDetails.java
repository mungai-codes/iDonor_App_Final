package net.mungai.idonor.admin.service;

import net.mungai.idonor.admin.model.Admin;
import net.mungai.idonor.app.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyAdminDetails implements UserDetails {

    private final Admin admin;

    public MyAdminDetails(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<Role> roles = admin.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
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
        return admin.isEnabled();
    }

    public Boolean getStatus(){
        return admin.isEnabled();
    }

    public String getHospital(){
        return admin.getHospital();
    }

    public String getEmail(){
        return admin.getEmail();
    }
}
