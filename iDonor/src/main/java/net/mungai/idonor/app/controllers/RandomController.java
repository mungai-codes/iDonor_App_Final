package net.mungai.idonor.app.controllers;

import net.mungai.idonor.app.service.AppealService;
import net.mungai.idonor.donor.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RandomController {

    @Autowired
    private AppealService appealService;

    @Autowired
    private DonorService donorService;


}
