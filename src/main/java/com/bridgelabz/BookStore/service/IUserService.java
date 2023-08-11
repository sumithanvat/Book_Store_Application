package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.dto.UserDTO;
import com.bridgelabz.BookStore.exception.EmailAlreadyExistsException;
import com.bridgelabz.BookStore.model.UserRegistration;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    String addUser(UserDTO userDTO) throws EmailAlreadyExistsException;

    String loginUser(String email_id, String password);

    String sendOtp(String email);

    boolean verifyOtp(String email, String otp);

    String generateOTP();

    String generateAndSendOtpForForgotPassword(String email);

    // Verify OTP and reset password
    void resetPassword(String email, String newPassword);

    List<UserRegistration> getAllUsers();

    Object getUserById(String token);

    UserRegistration updateUser(String email_id, UserDTO userDTO);

    String getToken(String email);

//    ResponseEntity<ResponseDTO> deleteUserById(Integer id);

    ResponseEntity<ResponseDTO> deleteUserById(Integer id);

    UserRegistration updateRecordById(Integer id, UserDTO userDTO);
}
