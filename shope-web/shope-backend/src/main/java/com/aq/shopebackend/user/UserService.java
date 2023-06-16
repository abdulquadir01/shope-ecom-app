package com.aq.shopebackend.user;

import com.aq.shopecommon.entity.Role;
import com.aq.shopecommon.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional
@EntityScan({"com.aq.shopecommon.entity", "com.aq.shopebackend.user"})
public class UserService {

    public static final int USERS_PER_PAGE = 5;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public Page<User> listUsersByPage(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1, USERS_PER_PAGE);
        return userRepository.findAll(pageable);
    }

    public User getUserById(Long id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
    }

    public User saveUser(User user) {
        boolean isUserUpdating = (user.getId() != null);

        if (isUserUpdating) {
            User exitingUser = userRepository.findById(user.getId()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(exitingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }

       return userRepository.save(user);
    }


    public void deleteById(Long id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if(countById == null || countById == 0){
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }

        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Long id, boolean enabled){

        userRepository.updateEnabledStatus(id, enabled);

    }





    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public boolean isEmailUnique(Long id, String email) {
        User userByEmail = userRepository.findByEmail(email);

        if (userByEmail == null) {
            return true;
        }

        boolean isNewUser = (id == null);

        if (isNewUser) {
            if (userByEmail != null)
                return false;
        } else {
            if (userByEmail.getId() != id) {
                return false;
            }
        }

        return true;
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }


}
