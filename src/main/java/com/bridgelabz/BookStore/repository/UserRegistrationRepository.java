package com.bridgelabz.BookStore.repository;

import com.bridgelabz.BookStore.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRegistrationRepository extends JpaRepository<UserRegistration,Integer> {
    @Query(value = "SELECT * FROM user_registration where email=:email_Id", nativeQuery = true)
    public UserRegistration findByEmailid(String email_Id);

    UserRegistration findByEmail(String email);

    void delete(Optional<UserRegistration> userToDelete);

    boolean existsByEmail(String email);
}

