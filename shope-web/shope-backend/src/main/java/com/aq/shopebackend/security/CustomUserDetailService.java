//package com.aq.shopebackend.security;
//
//import com.aq.shopebackend.user.UserRepository;
//import com.aq.shopecommon.entity.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//
//@Service
//public class CustomUserDetailService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public CustomUserDetailService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
////        Load User from DB by username
//        User user = null;
//        try {
//            user = userRepository.findByEmail(username)
//                    .orElseThrow(() -> new Exception("username or email"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return user;
//    }
//}
