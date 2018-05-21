package org.superbiz.struts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

//    private UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    private UserRepository userRepository;

    public UserController( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/addUser")
    public String addUserForm() {
        return "addUserForm";
    }

    @PostMapping(value = "/addUser", consumes = "application/x-www-form-urlencoded")
    public String addUserFormHandlingPost(@RequestParam("id") Long id,
                                          @RequestParam("firstName") String firstName,
                                          @RequestParam("lastName") String lastName) {
        User user = new User(id, firstName, lastName);
//        userService.add(user);
        userRepository.save(user);
        return "addedUser";
    }

    @GetMapping("/findUser")
    public String findUserForm() {
        return "findUserForm";
    }

    @PostMapping("/findUser")
    public String findUser(@RequestParam long id, Model model) {
//        User user = userService.find(id);
        User user = userRepository.findOne(id);

        if (user == null) {
            model.addAttribute("errorMessage", "User not found");
            return "findUserForm";
        }

        model.addAttribute("user", user);
        return "displayUser";
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
//        model.addAttribute("users", userService.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "displayUsers";
    }

}
