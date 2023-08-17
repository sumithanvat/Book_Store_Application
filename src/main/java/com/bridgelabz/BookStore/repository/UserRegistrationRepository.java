package com.bridgelabz.BookStore.repository;

import com.bridgelabz.BookStore.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration,Integer> {
    @Query(value = "SELECT * FROM User_Table where email=:email", nativeQuery = true)
    public UserRegistration findByEmailId(String email);

    UserRegistration findByEmail(String email);

    boolean existsByEmail(String email);
}

