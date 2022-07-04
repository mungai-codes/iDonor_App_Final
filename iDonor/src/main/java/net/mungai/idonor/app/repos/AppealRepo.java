package net.mungai.idonor.app.repos;

import net.mungai.idonor.app.models.DonationAppeal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AppealRepo extends JpaRepository<DonationAppeal, Long> {

    @Query
    List<DonationAppeal> findByApprovedTrue();

    @Query
    List<DonationAppeal> findByApprovedFalse();


    @Query("Select u.bloodType from DonationAppeal u")
    List<DonationAppeal> checkBloodType();

    @Query("Select u from DonationAppeal u where u.bloodType.name=:bloodType")
   List<DonationAppeal> customResult(String bloodType);

    @Query("Select u from DonationAppeal u where u.bloodType.name=:bloodType")
    Page<DonationAppeal> theResult(String bloodType, Pageable pageable);


    @Query("Select u from DonationAppeal u where u.bloodType.name =:bloodType and (u.recipientAddress.county like %:County% or u.recipientAddress.subCounty like %:subCounty% or u.recipientAddress.ward like %:ward%)")
    List<DonationAppeal> aBitMorePrecise(String bloodType, String County, String subCounty, String ward);

    @Modifying
    @Transactional
    @Query("update DonationAppeal u set u.approved =:approved where u.id =:id")
    void updateAppeal(@Param(value = "id") Long id, @Param(value = "approved") boolean approved);

    @Modifying
    @Transactional
    @Query("UPDATE DonationAppeal u set u.approved = true where u.id =:id")
    void approveAppeal(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update DonationAppeal u set u.approved = false  where u.id =:id")
    void rejectAppeal(@Param("id") Long id);

    @Query("Select d from DonationAppeal d where d.bloodType.name like %?1%")
    public List<DonationAppeal> searchByRequiredBloodType(String bloodType);

    @Query("Select bloodType.name from DonationAppeal ")
    public List<DonationAppeal> getAllAppealBloodTypes();

//    @Query("Select u from DonationAppeal u where u.bloodType.name like '%A+%' or u.bloodType.name like '%AB+%'")
//    public List<DonationAppeal> aPlus();
//
//    @Query("Select u from DonationAppeal u where u.bloodType.name like '%O+%' or u.bloodType.name like '%A+%' or u.bloodType.name like '%B+%' or u.bloodType.name like '%AB+%'")
//    public List<DonationAppeal> oPlus();
//
//    @Query("Select u from DonationAppeal u where u.bloodType.name like '%B+%' or u.bloodType.name like '%AB+%'")
//    public List<DonationAppeal> bPlus();
//
//    @Query("Select u from DonationAppeal u where u.bloodType.name like '%AB+%'")
//    public List<DonationAppeal> abPlus();
//
//    @Query("Select u from DonationAppeal u where u.bloodType.name like '%A+%' or u.bloodType.name like '%A-%' or u.bloodType.name like '%AB-%' or u.bloodType.name like '%AB+%'")
//    public List<DonationAppeal> aNegative();
//
//    @Query("Select u from DonationAppeal u where u.bloodType.name like '%A+%' or u.bloodType.name like '%O+%' " +
//            "or u.bloodType.name like '%B+%' or u.bloodType.name like '%AB+%' or u.bloodType.name like '%A-%' or u.bloodType.name like '%O-%'" +
//            "or u.bloodType.name like '%B-%' or u.bloodType.name like '%AB-%'")
//    public List<DonationAppeal> oNegative();
//
//    @Query("Select u from DonationAppeal u where u.bloodType.name like '%B+%' or u.bloodType.name like '%B-%' or u.bloodType.name like '%AB+%' or u.bloodType.name like '%AB-%'")
//    public List<DonationAppeal> bNegative();
//
//    @Query("Select u from DonationAppeal u where u.bloodType.name like '%AB+%' or u.bloodType.name like '%AB-%'")
//    public List<DonationAppeal> abNegative();

    @Query("Select u from DonationAppeal u where u.bloodType.name=:bloodType")
    public Page<DonationAppeal> pagingTest(String bloodType, Pageable pageable);

    @Query("Select u from DonationAppeal u where u.bloodType.name like '%A+%' or u.bloodType.name like '%AB+%'" +
            " and (u.recipientAddress.county =:county or u.recipientAddress.subCounty =:subCounty or u.recipientAddress.ward =:ward) and (u.approved =true)")
    Page<DonationAppeal> aPlusExtra(Pageable pageable, String county, String subCounty, String ward);

    @Query("Select u from DonationAppeal u where (u.bloodType.name like '%O+%' or u.bloodType.name like '%A+%' or u.bloodType.name like '%B+%' or u.bloodType.name like '%AB+%')" +
            "and (u.recipientAddress.county =:county or u.recipientAddress.subCounty =:subCounty or u.recipientAddress.ward =:ward) and (u.approved =true)")
    Page<DonationAppeal> oPlusExtra(Pageable pageable, String county, String subCounty, String ward);

    @Query("Select u from DonationAppeal u where (u.bloodType.name like '%B+%' or u.bloodType.name like '%AB+%') " +
            "and (u.recipientAddress.county =:county or u.recipientAddress.subCounty =:subCounty or u.recipientAddress.ward =:ward) and (u.approved =true) " )
    Page<DonationAppeal> bPlusExtra(Pageable pageable, String county, String subCounty, String ward);

    @Query("Select u from DonationAppeal u where (u.bloodType.name like '%AB+%')" +
            " and (u.recipientAddress.county =:county or u.recipientAddress.subCounty =:subCounty or u.recipientAddress.ward =:ward) and (u.approved =true) ")
    Page<DonationAppeal> abPlusExtra(Pageable pageable, String county, String subCounty, String ward);

    @Query("Select u from DonationAppeal u where (u.bloodType.name like '%A+%' or u.bloodType.name like '%A-%' or u.bloodType.name like '%AB-%' or u.bloodType.name like '%AB+%')" +
            "and (u.recipientAddress.county =:county or u.recipientAddress.subCounty =:subCounty or u.recipientAddress.ward =:ward) and (u.approved =true) ")
    Page<DonationAppeal> aNegativeExtra(Pageable pageable, String county, String subCounty, String ward);

    @Query("Select u from DonationAppeal u where (u.bloodType.name like '%A+%' or u.bloodType.name like '%O+%' " +
            "or u.bloodType.name like '%B+%' or u.bloodType.name like '%AB+%' or u.bloodType.name like '%A-%' or u.bloodType.name like '%O-%'" +
            "or u.bloodType.name like '%B-%' or u.bloodType.name like '%AB-%')" +
            "and (u.recipientAddress.county =:county or u.recipientAddress.subCounty =:subCounty or u.recipientAddress.ward =:ward) and u.approved =true ")
    Page<DonationAppeal> oNegativeExtra(Pageable pageable, String county, String subCounty, String ward);

    @Query("Select u from DonationAppeal u where (u.bloodType.name like '%B+%' or u.bloodType.name like '%B-%' or u.bloodType.name like '%AB+%' or u.bloodType.name like '%AB-%')" +
            "and (u.recipientAddress.county =:county or u.recipientAddress.subCounty =:subCounty or u.recipientAddress.ward =:ward)" +
            "and (u.approved =true)")
    Page<DonationAppeal> bNegativeExtra(Pageable pageable, String county, String subCounty, String ward);

    @Query("Select u from DonationAppeal u where (u.bloodType.name like '%AB+%' or u.bloodType.name like '%AB-%')" +
            "and (u.recipientAddress.county =:county or u.recipientAddress.subCounty =:subCounty or u.recipientAddress.ward =:ward) and (u.approved =true)")
    Page<DonationAppeal> abNegativeExtra(Pageable pageable, String county, String subCounty, String ward);


    @Query("select u.phoneNumber from DonationAppeal u where u.id =:id")
    String getPhoneNumberById(@Param("id") Long id);



}
