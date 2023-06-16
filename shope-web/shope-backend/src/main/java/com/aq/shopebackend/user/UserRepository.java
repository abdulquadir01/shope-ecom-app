package com.aq.shopebackend.user;

import com.aq.shopecommon.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface UserRepository extends PagingAndSortingRepository<User, Long> {

//   @Query("SELECT u FROM User u WHERE u.email = :email")
//   public User getUserByEmail(@Param("email") String email);

   User findByEmail(String email);

   Long countById(Long id);

   @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
   @Modifying
   void updateEnabledStatus(Long id, boolean enabled);


}