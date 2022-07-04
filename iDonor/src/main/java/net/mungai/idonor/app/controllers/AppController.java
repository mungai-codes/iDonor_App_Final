package net.mungai.idonor.app.controllers;

import net.mungai.idonor.admin.model.Admin;
import net.mungai.idonor.admin.service.AdminService;
import net.mungai.idonor.admin.service.MyAdminDetails;
import net.mungai.idonor.app.models.BloodType;
import net.mungai.idonor.app.models.DonationAppeal;
import net.mungai.idonor.app.models.RecipientAddress;
import net.mungai.idonor.app.repos.AppealRepo;
import net.mungai.idonor.app.repos.BloodTypeRepo;
import net.mungai.idonor.app.service.AppealService;
import net.mungai.idonor.app.twilio.MessageRequest;
import net.mungai.idonor.app.twilio.Twilioproperties;
import net.mungai.idonor.donor.models.Address;
import net.mungai.idonor.donor.models.Donor;
import net.mungai.idonor.donor.models.Gender;
import net.mungai.idonor.donor.repos.DonorRepo;
import net.mungai.idonor.donor.service.AddressService;
import net.mungai.idonor.donor.service.DonorService;
import net.mungai.idonor.donor.service.MyDonorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class AppController {

    private final Twilioproperties twilioproperties;

    @Autowired
    private DonorService donorService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private BloodTypeRepo bloodTypeRepo;
    @Autowired
    private AppealService appealService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private DonorRepo donorRepo;
    @Autowired
    private AppealRepo appealRepo;

    private MessageRequest messageRequest;

    public AppController(Twilioproperties twilioproperties) {
        this.twilioproperties = twilioproperties;
    }


    static Logger log = Logger.getLogger(AppController.class.getName());

    @RequestMapping("/search")
    public String hoPage(Model model, @Param("keyword") String keyword, @Param("add") String add, @Param("bloodHell") String bloodHell){
        List<Donor> someDonors = donorService.listAll(keyword);
        model.addAttribute("donorList",someDonors);
        List<Donor> donorAddress =donorService.addressSearch(add);
        model.addAttribute("think",donorAddress);
        model.addAttribute("keyword",keyword);
        model.addAttribute("add",add);
        String address = "juja";
        List<Donor> searchedDonors = donorService.searchWithDonorDetails(bloodHell,address);
        model.addAttribute("search",searchedDonors);
        model.addAttribute("bloodHell",bloodHell);
        model.addAttribute("hell",address);

        return "index";
    }

    @RequestMapping("/t")
    public String homePage() {

      return "homepage";
    }

    @RequestMapping("/admin/dashboard/donors")
    public String myDonors(Model model){
        List<Donor> list = donorService.getAllDonors();
        model.addAttribute("listOfDonors", list);
        return "admin/dashboard/donors";
    }

    @RequestMapping("/registration")
    public String registrationForm(Model model){
        Address address =new Address();
        Donor donor = new Donor();
        List<BloodType> listBloodTypes =bloodTypeRepo.findAll();
        model.addAttribute("address",address);
        model.addAttribute("donor",donor);
        model.addAttribute("listBloodTypes",listBloodTypes);

        return "app/register";
    }


    @RequestMapping(value = "/saveDonor", method = RequestMethod.POST)
    public String aSave(Donor donor, @ModelAttribute Address address){

        donorService.saveWithDefaultUserRole(donor);
        address.setDonor(donor);
        addressService.save(address);

        return "redirect:/";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveDonor(@ModelAttribute Address address, @ModelAttribute Donor donor) {

        address.setDonor(donor);
        donorService.saveWithDefaultUserRole(donor);
        addressService.save(address);

        return "redirect:/";
    }

    @GetMapping("/bloodtypes")
    public String listBloodTypes(Model model){
        List<BloodType> listBloodTypes =bloodTypeRepo.findAll();
        model.addAttribute("listBloodTypes",listBloodTypes);
        return "bloodtypes";

    }

    @GetMapping("/createBloodType")
    public String addBloodType(Model model){
        model.addAttribute("bloodType", new BloodType());

        return "addBloodType";
    }

    @RequestMapping(value = "/bloodtypes/save")
    public String addBloodType(BloodType bloodType){
        bloodTypeRepo.save(bloodType);
        return "redirect:/bloodtypes";
    }


    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/enableDonor/{id}")
    public String enableDonor(@PathVariable("id") Long id){
        donorService.enableDonor(id);
        return "redirect:/admin/dashboard/user_management";
    }

    @RequestMapping("/disableDonor/{id}")
    public String disableDonor(@PathVariable("id") Long id){
        donorService.disableDonor(id);
        return "redirect:/admin/dashboard/user_management";
    }

    @RequestMapping("/donor/setAvailable")
    public String setAvailable(@AuthenticationPrincipal MyDonorDetails loggedUser){
        Long id = loggedUser.seeId();
        donorService.setAvailable(id);
        return "redirect:/donor/dashboard";
    }

    @RequestMapping("/donor/setUnAvailable")
    public String setUnAvailable(@AuthenticationPrincipal MyDonorDetails loggedUser){
        Long id = loggedUser.seeId();
        donorService.setUnAvailable(id);
        return "redirect:/donor/dashboard";
    }

    @RequestMapping("/")
    public String testHomepage(Model model){
        RecipientAddress address= new RecipientAddress();
        model.addAttribute("newAppeal", new DonationAppeal());
        List<BloodType> listBloodTypes =bloodTypeRepo.findAll();
        model.addAttribute("address",address);
        model.addAttribute("listBloodTypes",listBloodTypes);
        return "homepage";
    }

    @GetMapping("/donor/login")
    public String donorLoginPage(){
        return "donor/login";
    }

    @GetMapping("/admin/login")
    public String adminLoginPage(){

        return "admin/login";
    }

    @RequestMapping("/donor/dashboard")
    public String donorDashboard(Model model, @AuthenticationPrincipal MyDonorDetails loggedUser, @RequestParam(defaultValue = "1") int page){
        String email = loggedUser.getUsername();
        Donor donor =donorService.getByEmail(email);
        model.addAttribute("donor",donor);
        String bloodType = loggedUser.seeBloodType().getName();
        List<DonationAppeal> custom = appealService.appealToDonorView(bloodType);
        model.addAttribute("appealTarget",custom);
        String county = loggedUser.seeAddress().getCounty();
        String subCounty = loggedUser.seeAddress().getSubCounty();
        String ward = loggedUser.seeAddress().getWard();
        List<DonationAppeal> preciseCustom = appealService.precision(bloodType,county,subCounty,ward);
        model.addAttribute("preciseAppeal",preciseCustom);
        model.addAttribute("bloody", bloodType);
        Boolean status = loggedUser.getStatus();
        model.addAttribute("donorStatus",status);
//        Boolean availability = loggedUser.getAvailability();
//        model.addAttribute("availability",availability);
        List<BloodType> listBloodTypes =bloodTypeRepo.findAll();
        model.addAttribute("listBloodTypes",listBloodTypes);
        Page<DonationAppeal> myPage = appealService.customPage(bloodType, PageRequest.of(page, 4, Sort.by("recipientAge").descending()));
        model.addAttribute("myPage",myPage);
        model.addAttribute("currentPage",myPage);

        String phoneNumber = loggedUser.seePhoneNumber();
        model.addAttribute("userPhoneNumber",phoneNumber);
        Date dob = loggedUser.seeDob();
        model.addAttribute("userDob",dob);
        Gender gender = loggedUser.seeGender();
        model.addAttribute("userGender",gender);


        String userBloodType = loggedUser.seeBloodType().getName();
        model.addAttribute("userBloodType", userBloodType);


        if(userBloodType.equalsIgnoreCase("A+")){
            Page<DonationAppeal> one = appealService.aPlusExtra(county,subCounty,ward,page);
            List<DonationAppeal> two = one.getContent();
            model.addAttribute("precise",two);
            log.info("A+");
        }
        else if(userBloodType.equalsIgnoreCase("AB+")){
            Page<DonationAppeal> one = appealService.abPlusExtra(county,subCounty,ward,page);
            List<DonationAppeal> two = one.getContent();
            model.addAttribute("precise",two);
            log.info("AB+");
        }else if(userBloodType.equalsIgnoreCase("B+")){
            Page<DonationAppeal> one = appealService.bPlusExtra(county,subCounty,ward,page);
            List<DonationAppeal> two = one.getContent();
            model.addAttribute("precise",two);
            log.info("B+");
        }else if (userBloodType.equalsIgnoreCase("O+")){
            Page<DonationAppeal> one = appealService.oPlusExtra(county,subCounty,ward,page);
            List<DonationAppeal> two = one.getContent();
            model.addAttribute("precise",two);
            log.info("O+");
        }else if (userBloodType.equalsIgnoreCase("A-")){
            Page<DonationAppeal> one = appealService.aNegativeExtra(county,subCounty,ward,page);
            List<DonationAppeal> two = one.getContent();
            model.addAttribute("precise",two);
            log.info("A-");
        }else if (userBloodType.equalsIgnoreCase("AB-")){
            Page<DonationAppeal> one = appealService.abNegativeExtra(county,subCounty,ward,page);
            List<DonationAppeal> two = one.getContent();
            model.addAttribute("precise",two);
            log.info("AB-");
        }else if (userBloodType.equalsIgnoreCase("B-")){
            Page<DonationAppeal> one = appealService.bNegativeExtra(county,subCounty,ward,page);
            List<DonationAppeal> two = one.getContent();
            model.addAttribute("precise",two);
            log.info("B-");
        }else if(userBloodType.equalsIgnoreCase("O-")){
            Page<DonationAppeal> one = appealService.oNegativeExtra(county,subCounty,ward,page);
            List<DonationAppeal> two = one.getContent();
            model.addAttribute("precise",two);
            log.info("O-");
        }else{
            log.info("failed process");
        }
        
        return "donor/dashboard";
    }

    @RequestMapping("/admin/dashboard")
    public String adminDashboard(@AuthenticationPrincipal MyAdminDetails loggedUser, Model model, @Param("bloodType") String  bloodType, @Param("address") String address,@RequestParam(defaultValue = "0") int page){

        List<BloodType> listBloodTypes =bloodTypeRepo.findAll();
        model.addAttribute("listBloodTypes",listBloodTypes);
        model.addAttribute("bloodType", new BloodType());
        Admin admin = new Admin();
        model.addAttribute("admin",admin);
        List<Admin> aOfListAdmins = adminService.listAll();
        model.addAttribute("listAdmins",aOfListAdmins);
        model.addAttribute("b",bloodType);
        model.addAttribute("a",address);
        String hospital = loggedUser.getHospital();
        model.addAttribute("c",hospital);
       String theMessage = "request from: " + hospital + " for bloodType: " + bloodType + " near you!";
        model.addAttribute("message",theMessage);
        String theMesage = "Your Emergency at: " + hospital + " your blood-type is requested: Ingnore if not relevant";
        model.addAttribute("check", theMesage);

      String email = loggedUser.getEmail();
      model.addAttribute("email",email);

        Boolean status = loggedUser.getStatus();
        model.addAttribute("adminStatus",status);

        if(bloodType != null && address != null){
            if (bloodType.equalsIgnoreCase("A+")) {
                model.addAttribute("search",donorRepo.aPlus(address));
            } else if (bloodType.equalsIgnoreCase("AB+")) {
                model.addAttribute("search",donorRepo.abPlus(address));
            } else if (bloodType.equalsIgnoreCase("B+")) {
                model.addAttribute("search",donorRepo.bPlus(address));
            } else if (bloodType.equalsIgnoreCase("O+")) {
                model.addAttribute("search",donorRepo.oPlus(address));
            } else if (bloodType.equalsIgnoreCase("A-")) {
                model.addAttribute("search",donorRepo.aNegative(address));
            } else if (bloodType.equalsIgnoreCase("AB-")) {
                model.addAttribute("search",donorRepo.abNegative(address));
            } else if (bloodType.equalsIgnoreCase("B-")) {
                model.addAttribute("search",donorRepo.bNegative(address));
            } else if (bloodType.equalsIgnoreCase("O-")) {
                model.addAttribute("search",donorRepo.oNegative(address));
            }
        }
        else {
            model.addAttribute("search",donorRepo.universalDonor());
        }



        return "admin/dashboard";
    }

    @RequestMapping("/custom")
    public String testicle(){
        return "custom";
    }

    @RequestMapping("/admin/messaging/{id}")
    public String pleaseWork(@PathVariable("id") Long id, @AuthenticationPrincipal MyAdminDetails loggedUser, Model model,@Param("bloody") String  bloody ){
        String number = donorService.getPhoneNumber(id);
        String hospital = loggedUser.getHospital();
        model.addAttribute("hospital", hospital);
        String thrMessage ="A request for blood-of-type: " + bloody + " at " + hospital + " please";
        Message message = Message.creator(new PhoneNumber(number),new PhoneNumber(twilioproperties.getFromNumber()),thrMessage).create();

        String status = message.getStatus().toString();

        if("sent".equals(status)||"queued".equals(status)){
            return "redirect:/admin/dashboard";
        }
        return "redirect:/homepage";
    }

    @RequestMapping("/something")
    public String page()
    {
        return "something";
    }

    @RequestMapping("/donor/custom/")
    public String appealToDonor(){

        return "redirect:/donor/dashboard";
    }

    @PostMapping("/donor/update")
    public String updateAccount(Donor donor,RedirectAttributes redirectAttributes,
    @AuthenticationPrincipal MyDonorDetails loggedUser, Model model){

        Long id = loggedUser.seeId();
        donorService.updateUser(donor.getFirstName(),donor.getSurname(),donor.getDateOfBirth(),donor.getBloodType(),donor.getGender(),donor.getPhoneNumber(),donor.getPassword(),id);
        donorService.updateUserAddress(donor.getAddress().getCounty(),donor.getAddress().getSubCounty(),donor.getAddress().getWard(),id);

        return "redirect:/donor/dashboard";
    }

    @RequestMapping("/donor/dashboard/paging")
    public String pagingTest(@AuthenticationPrincipal MyDonorDetails loggedUser, Model model,
    @RequestParam(defaultValue = "1") int page){

        String bloodType = loggedUser.seeBloodType().getName();


        Page<DonationAppeal> pagingTest = appealService.pagingTest(bloodType, page);
        Long totalItems = pagingTest.getTotalElements();
        int totalPages = pagingTest.getTotalPages();

        List<DonationAppeal> listThem = pagingTest.getContent();
        model.addAttribute("pagingTest",listThem);

        model.addAttribute("totalItems",totalItems);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",totalPages);

        return "/donor/dashboard/paging";
    }

    @RequestMapping("/admin/dashboard/user_management")
    public String administrator(Model model,@RequestParam(defaultValue = "1") int page, @AuthenticationPrincipal MyAdminDetails loggedUser){

        Page<Donor> approved = donorService.approvedDonors(page);
        List<Donor> approvedDonors = approved.getContent();
        model.addAttribute("approvedDonors",approvedDonors);
        Page<Donor> pendingApproval = donorService.pendingDonors(page);
        List<Donor> pendingDonor = pendingApproval.getContent();
        model.addAttribute("pending", pendingDonor);

        Boolean status = loggedUser.getStatus();
        model.addAttribute("adminStatus",status);

        return "admin/dashboard/user_management";
    }

    @RequestMapping("/admin/dashboard/user-management/approved")
    public String approvedUsers(Model model,@RequestParam(defaultValue = "1") int page){

        Page<Donor> approved = donorService.approvedDonors(page);
        Long totalItems = approved.getTotalElements();
        int totalPages = approved.getTotalPages();


        List<Donor> approvedDonors = approved.getContent();
        model.addAttribute("approvedDonors",approvedDonors);

        model.addAttribute("totalItems",totalItems);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",totalPages);

        return "admin/dashboard/user-management/approved";
    }

    @RequestMapping("/admin/dashboard/user-management/all-users")
    public String pendingUsers(Model model,@RequestParam(defaultValue = "1") int page, @AuthenticationPrincipal MyAdminDetails loggedUser){
        Page<Donor> all = donorService.findAll(page);
        Long totalItems = all.getTotalElements();
        int totalPages = all.getTotalPages();

        List<Donor> donors = all.getContent();
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",totalPages);

        model.addAttribute("donors",donors);

        Boolean status = loggedUser.getStatus();
        model.addAttribute("adminStatus",status);

        return "pending";
    }


    @RequestMapping("/message/{id}")
    public String work(@PathVariable("id") Long id,@AuthenticationPrincipal MyAdminDetails loggedUser, Model model){

        String number = donorService.getPhoneNumber(id);
        String hospital = loggedUser.getHospital();

        Message message = Message.creator(new PhoneNumber(number),new PhoneNumber(twilioproperties.getFromNumber()), "hmmm").create();

        String theMessage = "Your Emergency at: " + hospital + " your blood-type is requested: Ingnore if not relevant";
        model.addAttribute("print", theMessage);

        String status = message.getStatus().toString();

        if("sent".equals(status)||"queued".equals(status)){
            return "redirect:/admin/dashboard";
        }
        return "redirect:/homepage";
    }


    @RequestMapping("/action")
    public String adminAction(@AuthenticationPrincipal MyAdminDetails loggedUser,
                              Model model, @Param("bloodType") String  bloodType, @Param("address") String address){

        if(bloodType != null && address != null){
            if (bloodType.equalsIgnoreCase("A+")) {
                model.addAttribute("search",donorRepo.aPlus(address));
            } else if (bloodType.equalsIgnoreCase("AB+")) {
                model.addAttribute("search",donorRepo.abPlus(address));
            } else if (bloodType.equalsIgnoreCase("B+")) {
                model.addAttribute("search",donorRepo.bPlus(address));
            } else if (bloodType.equalsIgnoreCase("O+")) {
                model.addAttribute("search",donorRepo.oPlus(address));
            } else if (bloodType.equalsIgnoreCase("A-")) {
                model.addAttribute("search",donorRepo.aNegative(address));
            } else if (bloodType.equalsIgnoreCase("AB-")) {
                model.addAttribute("search",donorRepo.abNegative(address));
            } else if (bloodType.equalsIgnoreCase("B-")) {
                model.addAttribute("search",donorRepo.bNegative(address));
            } else if (bloodType.equalsIgnoreCase("O-")) {
                model.addAttribute("search",donorRepo.oNegative(address));
            }
        }
        else {
            model.addAttribute("search",donorRepo.universalDonor());
        }

        model.addAttribute("bloode", bloodType);

        return "action";
    }


    @RequestMapping("/admin/deleteDonor/{id}")
    public String adminDonorDeletion(@PathVariable("id") Long id, Donor donor){
        donorService.delete(id);
        return "redirect:/admin/dashboard/user_management";
    }


    @RequestMapping("/admin/dashboard/appeal-manager")
    public String appealManager(Model model, @AuthenticationPrincipal MyAdminDetails loggedUser){
        List<DonationAppeal> approvedAppeal = appealService.existingAppeals();
        model.addAttribute("appealList",approvedAppeal);
        Boolean status = loggedUser.getStatus();
        model.addAttribute("adminStatus",status);
        return "admin/dashboard/appeals";
    }
    
    
    @RequestMapping("/donor/appeal-application/{id}")
    public String appealApplication(@PathVariable("id") Long id, @AuthenticationPrincipal MyDonorDetails loggedUser, Model model){

        String newLine = System.getProperty("line.separator");
        String phoneNumber = appealService.getPhoneNumber(id);

        String email = loggedUser.seeUsername();
        String donorNumber=  loggedUser.seePhoneNumber();
        Gender gender = loggedUser.seeGender();
        String bloodType = loggedUser.seeBloodType().getName();
        String applicationMessage = "User: " + email + " with details: " + "/n" + "Phone-Number: "
                + donorNumber + "." + "/n" + "gender: " + gender + "." +
                "/n" + "and Blood-Type: " + bloodType + "!" + "/n" + "applied to your appeal";
        Message message = Message.creator(new PhoneNumber(phoneNumber),new PhoneNumber(twilioproperties.getFromNumber()),applicationMessage).create();

        String status = message.getStatus().toString();

        if("sent".equals(status)||"queued".equals(status)){
            return "redirect:/donor/dashboard";
        }

        return "redirect:/donor/dashboard";
    }

    @RequestMapping("/admin/deleteAppeal/{id}")
    public String adminAppealDeletion(@PathVariable("id") Long id, Donor donor){
        appealService.deleteAppealById(id);
        return "redirect:/admin/dashboard/appeal-manager";
    }




    @RequestMapping("/find_all")
    String findAllDonors(@RequestParam(defaultValue = "1") int page,Model model){
        Page<Donor> myDonor = donorService.aTest(page);
        List<Donor> aDonor = myDonor.getContent();
        model.addAttribute("approvedDonors", aDonor);
        return "findAll";
    }


    @RequestMapping("/create")
    public String createAdmin(){
        return "admin_creation";
    }

    }

