package com.aq.shopebackend.user;


import com.aq.shopebackend.utils.FileUploadUtil;
import com.aq.shopecommon.entity.Role;
import com.aq.shopecommon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public String getAllUsers(Model model) {
//        List<User> userList = userService.getAllUsers();
//        model.addAttribute("userList", userList);
//        return "user.html";
        return getUsersByPage(1, model);
    }

    @GetMapping("/pages/{pageNumber}")
    public String getUsersByPage(@PathVariable int pageNumber, Model model){
        Page<User> userPage = userService.listUsersByPage(pageNumber);
        List<User> userList =  userPage.getContent();

        Long startCount = (long) (pageNumber - 1) * UserService.USERS_PER_PAGE + 1;
        Long endCount = startCount + UserService.USERS_PER_PAGE - 1;

        if (endCount > userPage.getTotalElements()){
            endCount = userPage.getTotalElements();
        }

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("userList", userList);

        return "user.html";
    }


    @GetMapping("/new")
    public String createNewUser(Model model) {
        List<Role> listOfRoles = userService.getAllRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listOfRoles", listOfRoles);
        model.addAttribute("pageTitle", "Create New User");

        return "user_form.html";
    }

    @PostMapping("/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("profilePhoto") MultipartFile multipartFile) throws IOException {
        System.out.println(user);
        System.out.println(multipartFile.getOriginalFilename());

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setPhotos(fileName);

            User savedUser = userService.saveUser(user);

            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }else {
            if(user.getPhotos().isEmpty()) {
                user.setPhotos(null);
            }
            userService.saveUser(user);
        }

        redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");

        return "redirect:/users";
    }

    @GetMapping("/edit/{userId}")
    public String editUser(@PathVariable Long userId, RedirectAttributes redirectAttributes, Model model) {
        User user = null;
        List<Role> listOfRoles = userService.getAllRoles();
        try {
            user = userService.getUserById(userId);
            model.addAttribute("user", user);
            model.addAttribute("listOfRoles", listOfRoles);
            model.addAttribute("pageTitle", "Edit User (ID: " + userId + ")");
            return "user_form.html";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteById(userId);
            redirectAttributes.addFlashAttribute("message", "The user Id " + userId + " had been deleted successfully");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }


    @GetMapping("/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Long id,
                                          @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {

        userService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";

        String message = "The user ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/users";

    }

}
