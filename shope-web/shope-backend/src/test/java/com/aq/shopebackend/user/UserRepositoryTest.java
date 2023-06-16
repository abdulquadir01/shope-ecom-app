package com.aq.shopebackend.user;


import com.aq.shopecommon.entity.Role;
import com.aq.shopecommon.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class, 1l);
        User userJohn = new User("John", "Doe", "jhondoe@gmail.com","password");
        userJohn.addRole(roleAdmin);

        User savedUser = userRepository.save(userJohn);

        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateUserWithTwoRole(){
        User userRavi = new User("Ravi", "Kumar", "ravikumar@gmail.com","password");

        Role roleEditor = new Role(3L);
        Role roleAssistant = new Role(5L);
        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssistant);

        User savedUser = userRepository.save(userRavi);

        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAllUsers(){
        List<User> userList = (List<User>) userRepository.findAll();
        userList.forEach(System.out::println);
    }

    @Test
    public void testGetUserById(){
        User userJohn = userRepository.findById(1L).get();

        assertThat(userJohn).isNotNull();
    }

    @Test
    public void testUpdateUser(){
        User userJohn = userRepository.findById(1L).get();
        userJohn.setEnabled(true);

//        System.out.println(userJohn);
        userRepository.save(userJohn);
    }

    @Test
    public void testUpdateUserRoles(){
        User userRavi = userRepository.findById(2L).get();
        Role roleEditor = new Role(3L);
        Role roleSalesperson = new Role(2L);

        userRavi.getRoles().remove(roleEditor);
        userRavi.addRole(roleSalesperson);

        User savedUser = userRepository.save(userRavi);

        System.out.println(savedUser);

    }


    @Test
    public void testDeleteUser(){
        userRepository.deleteById(2L);
    }


    @Test
    public void testCountById() {
        Long id = 4L;
        Long countById = userRepository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisableUser(){
        Long id = 4L;
        userRepository.updateEnabledStatus(id, false);
    }

    @Test
    public void testEnableUser(){
        Long id = 4L;
        userRepository.updateEnabledStatus(id, true);
    }


    @Test
    public void testListFirstPage(){
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);

        List<User> userList = page.getContent();

        userList.forEach(System.out::println);

        assertThat(userList.size()).isEqualTo(pageSize);
    }

}