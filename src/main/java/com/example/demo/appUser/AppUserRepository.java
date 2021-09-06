package com.example.demo.appUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    // SELECT * FROM student WHERE email= ?
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByUsername(String username);
}
