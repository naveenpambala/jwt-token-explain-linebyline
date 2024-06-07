package com.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, String> {

}
