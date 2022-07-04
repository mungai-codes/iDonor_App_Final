package net.mungai.idonor.app.controllers;

import net.mungai.idonor.app.models.BloodType;
import net.mungai.idonor.app.repos.BloodTypeRepo;
import net.mungai.idonor.app.service.AppealService;
import net.mungai.idonor.app.models.DonationAppeal;
import net.mungai.idonor.app.repos.ReAddressRepo;
import net.mungai.idonor.app.models.RecipientAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class AppealController {

    @Autowired
    private AppealService appealService;
    @Autowired
    private ReAddressRepo reAddressRepo;
    @Autowired
    private BloodTypeRepo bloodTypeRepo;

    @RequestMapping("/appeal")
    public String makeAppeal(Model model){
        RecipientAddress address= new RecipientAddress();
        model.addAttribute("newAppeal", new DonationAppeal());
        List<BloodType> listBloodTypes =bloodTypeRepo.findAll();
        model.addAttribute("address",address);
        model.addAttribute("listBloodTypes",listBloodTypes);
        return "makeAppeal";
    }

    @RequestMapping(value = "/appeal/save", method = RequestMethod.POST)
    public String saveAppeal(@ModelAttribute RecipientAddress address, @ModelAttribute DonationAppeal appeal) throws IOException {

        address.setAppeal(appeal);
        appealService.saveAppeal(appeal);
        reAddressRepo.save(address);

        return "redirect:/";
    }


    @RequestMapping("/approved")
    private String viewApprovedAppeals(Model model) {
        List<DonationAppeal> approvedAppeal = appealService.approvedAppeal();
        model.addAttribute("appealList",approvedAppeal);
        return "view";
    }

    @RequestMapping("/approved/all")
    public String viewAllAppeals(Model model){
        List<DonationAppeal> approvedAppeal = appealService.existingAppeals();
        model.addAttribute("appealList",approvedAppeal);

        return "view_all";
    }

    @RequestMapping("/all/update/{id}")
    public String approveAppeal(@PathVariable("id") Long id){
        appealService.approveAppeal(id);
        return "redirect:/admin/dashboard/appeal-manager";
    }

    @RequestMapping("/all/reject/{id}")
    public String rejectAppeal(@PathVariable("id") Long id){
        appealService.rejectAppeal(id);
        return "redirect:/admin/dashboard/appeal-manager";
    }

}
