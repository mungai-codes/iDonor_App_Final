package net.mungai.idonor.donor.models;

import net.mungai.idonor.app.models.BloodType;
import net.mungai.idonor.app.models.Role;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "donors")
public class Donor extends DonorBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = true, length = 64)
    private String profilePic;
    @Column(unique = true, nullable = false)
    private String email;
    private String firstName;
    private String surname;
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "donor_bloodtype_id")
    private BloodType bloodType;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    private boolean available;
    private boolean enabled;
    private String password;

    @OneToOne(mappedBy = "donor", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "donor_roles",
    joinColumns = @JoinColumn(name = "donor_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<Role>();

    public Donor() {

    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password =   new BCryptPasswordEncoder().encode(password) ;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role donorRole) {
        this.roles.add(donorRole);
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = true;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "id=" + id +
                ", profilePic='" + profilePic + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", bloodType=" + bloodType +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", roles=" + roles +
                '}';
    }

    public void removeRoles(Role role){
        this.roles.remove(role);
    }
}
