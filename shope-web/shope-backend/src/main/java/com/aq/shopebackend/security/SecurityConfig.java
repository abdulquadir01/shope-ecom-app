//package com.aq.shopebackend.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import java.util.List;
//
//
//
//
////@Configuration
////@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true)
////@EnableWebMvc
//public class SecurityConfig {
//
//    public static final String[] PUBLIC_URLS = {
//            "/ShopeAdmin/**",
//            "/ShopeAdmin/users/**",
//            "/ShopeAdmin/webjars/**",
//    };
//
//    private final CustomUserDetailService userDetailService;
//    private final JwtAuthenticationEntryPoint authEntryPoint;
//    private final JwtAuthenticationFilter authFilter;
//
//    public SecurityConfig(CustomUserDetailService userDetailService, JwtAuthenticationEntryPoint authEntryPoint, JwtAuthenticationFilter authFilter) {
//        this.userDetailService = userDetailService;
//        this.authEntryPoint = authEntryPoint;
//        this.authFilter = authFilter;
//    }
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .cors()
//                .configurationSource(corsConfigurationSource())
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers(PUBLIC_URLS).permitAll()
//                .antMatchers(HttpMethod.GET).permitAll()            //allow all GET request
//                .anyRequest()
//                .authenticated()
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(authEntryPoint)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        httpSecurity.authenticationProvider(daoAuthenticationProvider());
//        httpSecurity.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return httpSecurity.build();
//    }
//
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedOriginPatterns(List.of("*"));
//        configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("Accept","Authorization","Content-Type", "Body"));
//        configuration.setMaxAge(1800L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider () throws Exception {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailService);
//        provider.setPasswordEncoder(passwordEncoder());
//
//        return provider;
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//}
