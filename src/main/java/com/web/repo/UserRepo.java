package com.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {

}