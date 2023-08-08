package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.UserDTO;
import com.bridgelabz.BookStore.model.UserRegistration;

import java.util.List;

public interface UserServiceI {
    String addUser(UserDTO userDTO);

    List<UserRegistration> getAllUsers();

    Object getUserById(String token);

    int loginUserTest(String email_id, String password);

    String loginUser(String email_id, String password);

    String forgotPassword(String email, String password);

    Object getUserByEmailId(String emailId);

    UserRegistration updateUser(String email_id, UserDTO userDTO);

    String getToken(String email);

    List<UserRegistration> getAllUserDataByToken(String token);

    UserRegistration updateRecordByToken(Integer id, UserDTO userDTO);
}
