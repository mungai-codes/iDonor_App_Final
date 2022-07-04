package net.mungai.idonor.app.controllers;

import net.mungai.idonor.admin.model.Admin;
import net.mungai.idonor.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/open")
    public String adminPage(Model model){
        Admin admin = new Admin();
        model.addAttribute("admin",admin);
        return "admin_creation";
    }

    @RequestMapping("/adminview")
    public String existingAdmins(Model model){
        List<Admin> aOfListAdmins = adminService.listAll();
        model.addAttribute("listAdmins",aOfListAdmins);

        return "list_Admins";
    }

    @RequestMapping(value = "/admin_save", method = RequestMethod.POST)
    public String saveDonor(@ModelAttribute Admin admin) throws IOException {

        adminService.saveWithDefaultUserRole(admin);

        return "redirect:/admin/dashboard";
    }

    @RequestMapping("/enable/{id}")
    public String enableDonor(@PathVariable("id") Long id){
        adminService.enableAdmin(id);
        return "redirect:/admin/dashboard";
    }

    @RequestMapping("/disable/{id}")
    public String disableDonor(@PathVariable("id") Long id){
        adminService.disableAdmin(id);
        return "redirect:/admin/dashboard";
    }

}
