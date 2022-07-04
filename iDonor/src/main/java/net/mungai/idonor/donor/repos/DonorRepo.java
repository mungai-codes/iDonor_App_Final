package net.mungai.idonor.donor.repos;

import net.mungai.idonor.app.models.BloodType;
import net.mungai.idonor.app.models.DonationAppeal;
import net.mungai.idonor.donor.models.Donor;
import net.mungai.idonor.donor.models.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Repository
public interface DonorRepo extends JpaRepository<Donor, Long> {

    @Query("select  u from Donor u where u.email = :email")
    public Donor getDonorByEmail(@Param("email") String email);


    @Query("SELECT d from Donor d  where d.bloodType.name like %?1%")
    public List<Donor> search(String keyword);

    @Query("Select d from Donor d  where d.address.county  like %?1%" +
            "or d.address.subCounty like %?1%" +
            "or d.address.ward like %?1%")
    public List<Donor> searchByAddress(String keyword);

    public Donor findDonorByAddress_CountyAndAddress_SubCountyAndAddress_Ward(String address_county, String address_subCounty, String address_ward);

    @Modifying
    @Transactional
    @Query("UPDATE Donor u set u.enabled = true where u.id =:id")
    void enableDonor(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Donor u set u.enabled = false where u.id =:id")
    void disableDonor(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Donor u set u.available = true where u.id =:id")
    void setAvailable(@Param("id")Long id);

    @Modifying
    @Transactional
    @Query("update Donor u set u.available = false where u.id =:id")
    void setUnAvailable(@Param("id")Long id);

    @Query("select u.phoneNumber from Donor u where u.id =:id")
    String getPhoneNumberById(@Param("id") Long id);


    @Modifying
    @Transactional
    @Query("UPDATE Donor u set u.firstName =:firstname, u.surname=:surname, u.dateOfBirth=:dob, u.bloodType=:bloodType, u.gender=:gender, u.phoneNumber=:phonenumber, u.password=:password where u.id =:id")
    void updateDonor(String firstname, String surname, Date dob, BloodType bloodType, Gender gender,String phonenumber,String password, Long id);


    @Modifying
    @Transactional
    @Query("Update Address u set u.county=:county, u.subCounty=:subcounty, u.ward=:ward where u.id=:id")
    void updateDonorAddress(String county, String subcounty, String ward, Long id);

    Page<Donor> findDonorByEnabledTrue(Pageable pageable);

    Page<Donor> findDonorByEnabledFalse(Pageable pageable);

    @Query("select u from Donor u where u.enabled=false")
    Page<Donor> disabledDonors(Pageable pageable);

    @Query("select u.bloodType.name from Donor u")
    List<Donor> getBloodTypes();

    @Query("Select u from Donor u where u.bloodType.name like ?1% and (u.address.county like ?2 or u.address.subCounty like ?2 or u.address.ward like ?2)")
    List<Donor> searchWithDonorDetails(@Param("bloodType") String bloodType, @Param("address") String address);


    @Query("Select u from Donor u where (u.bloodType.name like '%A+%' or u.bloodType.name like '%A-%'" +
            "or u.bloodType.name like '%O+%' or u.bloodType.name like '%O-%')" +
            " and (u.address.county like ?1 or u.address.subCounty like ?1 or u.address.ward like ?1)")
    List<Donor> aPlus(String address);

    @Query("Select u from Donor u where (u.bloodType.name like '%O+%' or u.bloodType.name like '%O-%')" +
            " and (u.address.county like ?1 or u.address.subCounty like ?1 or u.address.ward like ?1)")
    List<Donor> oPlus(String address);

    @Query("Select u from Donor u where (u.bloodType.name like '%B+%' or u.bloodType.name like '%B-%'" +
            "or u.bloodType.name like '%O+%' or u.bloodType.name like '%O-%')" +
            " and (u.address.county like ?1 or u.address.subCounty like ?1 or u.address.ward like ?1)")
    List<Donor> bPlus(String address);

    @Query("Select u from Donor u where (u.bloodType.name like '%A+%' or u.bloodType.name like '%O+%' " +
            "or u.bloodType.name like '%B+%' or u.bloodType.name like '%AB+%' or u.bloodType.name like '%A-%' or u.bloodType.name like '%O-%'" +
            "or u.bloodType.name like '%B-%' or u.bloodType.name like '%AB-%')" +
            "and (u.address.county like %:address% or u.address.subCounty like %:address% or u.address.ward like %:address%)")
    List<Donor> abPlus(String address);

    @Query("Select u from Donor u where (u.bloodType.name like '%O-%' or u.bloodType.name like '%A-%')" +
            " and (u.address.county like ?1 or u.address.subCounty like ?1 or u.address.ward like ?1)")
   List<Donor> aNegative(String address);

    @Query("Select u from Donor u where (u.bloodType.name like '%O-%')" +
            "and (u.address.county like %:address% or u.address.subCounty like %:address% or u.address.ward like %:address%)")
   List<Donor> oNegative(String address);

    @Query("Select u from Donor u where (u.bloodType.name like '%O-%' or u.bloodType.name like '%B-%')" +
            "and (u.address.county like ?1 or u.address.subCounty like ?1 or u.address.ward like ?1)")
    List<Donor> bNegative(String address);

    @Query("Select u from Donor u where (u.bloodType.name like '%A-%' or u.bloodType.name like '%AB-%'" +
            "or u.bloodType.name like '%B-%' or u.bloodType.name like '%O-%')" +
            " and (u.address.county like ?1 or u.address.subCounty like ?1 or u.address.ward like ?1)")
    List<Donor> abNegative(String address);

    @Query("select u from Donor u where u.bloodType.name like 'O-%'")
    List<Donor> universalDonor();



}
