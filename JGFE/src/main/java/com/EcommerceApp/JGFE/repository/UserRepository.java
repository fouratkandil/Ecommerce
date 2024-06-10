package com.EcommerceApp.JGFE.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EcommerceApp.JGFE.entity.user;
import com.EcommerceApp.JGFE.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<user, Long> {

    Optional<user> findFirstByEmail(String email);

    user findByRole(UserRole userRole);

}
