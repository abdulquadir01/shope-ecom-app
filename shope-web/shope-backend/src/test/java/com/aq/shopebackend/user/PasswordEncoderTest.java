package com.aq.shopebackend.user;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("password");

        System.out.println(encodedPassword);

        boolean result = passwordEncoder.matches("password", encodedPassword );

        System.out.println(result);
    }




}
