package com.aq.shopebackend.user;


import com.aq.shopecommon.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class RoleRepositoryTest  {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testAddRoles(){
        Role roleAdmin = new Role("Admin", "Manage everything");
        Role roleSalesperson = new Role("Salesperson", "Manage product price, customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "Manage categories, brands, products, articles and menus");
        Role roleShipper = new Role("Shipper", "View products, view orders and update order status");
        Role roleAssistant = new Role("Assistant", "Manage questions and reviews");


        roleRepository.saveAll(List.of(roleAdmin, roleSalesperson, roleEditor, roleShipper, roleAssistant));
    }

}