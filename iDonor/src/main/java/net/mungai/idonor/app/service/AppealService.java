package net.mungai.idonor.app.service;

import net.mungai.idonor.app.models.DonationAppeal;
import net.mungai.idonor.app.repos.AppealRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AppealService {

    @Autowired
    private AppealRepo appealRepo;

    public List<DonationAppeal> precision(String bloodType,String County, String subCounty, String ward){
        return appealRepo.aBitMorePrecise(bloodType,County,subCounty,ward);
    }

    public List<DonationAppeal> existingAppeals(){
       return appealRepo.findAll();
    }

    public List<DonationAppeal> approvedAppeal(){
        return appealRepo.findByApprovedTrue();
    }

    public List<DonationAppeal> unApprovedAppeal(){
        return appealRepo.findByApprovedFalse();
    }

    public List<DonationAppeal> appealToDonorView(String bloodType){
        return appealRepo.customResult(bloodType);
    }

    public Page<DonationAppeal> customPage(String bloodType, PageRequest page){
        return appealRepo.theResult(bloodType, page);
    }

    public void saveAppeal(DonationAppeal appeal){
        appealRepo.save(appeal);
    }

    public void saveApprovedAppeal(Long id, boolean approved){
        appealRepo.updateAppeal(id, approved);
    }

    public void approveAppeal(Long id){
        appealRepo.approveAppeal(id);
    }

    public void rejectAppeal(Long id){
        appealRepo.rejectAppeal(id);
    }

    public DonationAppeal get(Long id) {
        return appealRepo.getById(id);
    }

    public List<DonationAppeal> appealSearch(String keyword){
        if(keyword != null){
            return appealRepo.searchByRequiredBloodType(keyword);
        }
        System.err.println("address" + keyword + " not found!?");
        return appealRepo.findAll();
    }



    public Page<DonationAppeal> pagingTest(String bloodType, int page){
        Pageable pageable = PageRequest.of(page - 1,1, Sort.by("id"));
        return appealRepo.pagingTest(bloodType, pageable);
    }



    public Page<DonationAppeal> aPlusExtra(String county, String subCounty, String ward,int page){
        Pageable pageable = PageRequest.of(page-1,1, Sort.by("id").descending());
        return appealRepo.aPlusExtra(pageable,county,subCounty,ward);
    }

    public Page<DonationAppeal> oPlusExtra(String county, String subCounty, String ward,int page){
        Pageable pageable = PageRequest.of(page-1,1, Sort.by("id").descending());
        return appealRepo.oPlusExtra(pageable,county,subCounty,ward);
    }

    public Page<DonationAppeal> bPlusExtra(String county, String subCounty, String ward,int page){
        Pageable pageable = PageRequest.of(page-1,1, Sort.by("id").descending());
        return appealRepo.bPlusExtra(pageable,county,subCounty,ward);
    }

    public Page<DonationAppeal> abPlusExtra(String county, String subCounty, String ward,int page){
        Pageable pageable = PageRequest.of(page-1,1, Sort.by("id").descending());
        return appealRepo.abPlusExtra(pageable,county,subCounty,ward);
    }

    public Page<DonationAppeal> aNegativeExtra(String county, String subCounty, String ward,int page){
        Pageable pageable = PageRequest.of(page-1,1, Sort.by("id").descending());
        return appealRepo.aNegativeExtra(pageable,county,subCounty,ward);
    }

    public Page<DonationAppeal> oNegativeExtra(String county, String subCounty, String ward,int page){
        Pageable pageable = PageRequest.of(page-1,1, Sort.by("id").descending());
        return appealRepo.oNegativeExtra(pageable,county,subCounty,ward);
    }

    public Page<DonationAppeal> bNegativeExtra(String county, String subCounty, String ward,int page){
        Pageable pageable = PageRequest.of(page-1,1, Sort.by("id").descending());
        return appealRepo.bNegativeExtra(pageable,county,subCounty,ward);
    }

    public Page<DonationAppeal> abNegativeExtra(String county, String subCounty, String ward,int page){
        Pageable pageable = PageRequest.of(page-1,1, Sort.by("id").descending());
        return appealRepo.abNegativeExtra(pageable,county,subCounty,ward);
    }

    public String getPhoneNumber(Long id){
        return appealRepo.getPhoneNumberById(id);
    }

    @Transactional
    @Modifying
    public void deleteAppealById(Long id){
        appealRepo.deleteById(id);
    }


}
