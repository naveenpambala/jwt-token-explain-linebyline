package com.web.service;

 
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.entity.Role;
import com.web.entity.Users;
import com.web.repo.RoleRepo;
import com.web.repo.UserRepo;

@Service
public class UsersService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	  public void initRoleAndUser() {
//
//	        Role adminRole = new Role();
//	        adminRole.setRoleName("Admin");
//	        adminRole.setRoleDiscription("Admin role");
//	        roleRepo.save(adminRole);
//
//	        Role userRole = new Role();
//	        userRole.setRoleName("User");
//	        userRole.setRoleDiscription("Default role for newly created record");
//	        roleRepo.save(userRole);
//
//	        Users adminUser = new Users();
//	        adminUser.setUserName("admin123");
//	        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
//	        adminUser.setFirstName("admin");
//	        adminUser.setLastName("admin");
//	        Set<Role> adminRoles = new HashSet<>();
//	        adminRoles.add(adminRole);
//	        adminUser.setRole(adminRoles);
//	        userRepo.save(adminUser);

//	        User user = new User();
//	        user.setUserName("raj123");
//	        user.setUserPassword(getEncodedPassword("raj@123"));
//	        user.setUserFirstName("raj");
//	        user.setUserLastName("sharma");
//	        Set<Role> userRoles = new HashSet<>();
//	        userRoles.add(userRole);
//	        user.setRole(userRoles);
//	        userRepo.save(user);
	    }

	    public Users registerNewUser(Users users) {
	        Role role = roleRepo.findById("User").get();
	        Set<Role> userRoles = new HashSet<>();
	        userRoles.add(role);
	        users.setRole(userRoles);
	        users.setUserPassword(getEncodedPassword(users.getUserPassword()));

	        return userRepo.save(users);
	    }

	    public String getEncodedPassword(String password) {
	        return passwordEncoder.encode(password);
	    }

}
