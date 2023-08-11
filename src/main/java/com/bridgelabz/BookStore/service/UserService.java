package com.bridgelabz.BookStore.service;

import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.dto.UserDTO;
import com.bridgelabz.BookStore.exception.BookStoreException;
import com.bridgelabz.BookStore.exception.EmailAlreadyExistsException;
import com.bridgelabz.BookStore.exception.UserNotFoundException;
import com.bridgelabz.BookStore.model.UserRegistration;
import com.bridgelabz.BookStore.repository.UserRegistrationRepository;
import com.bridgelabz.BookStore.util.EmailSenderService;
import com.bridgelabz.BookStore.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRegistrationRepository userRepository;
    @Autowired
    EmailSenderService mailService;
    @Autowired
    TokenUtility util;

    @Override
    public String addUser(UserDTO userDTO) throws EmailAlreadyExistsException {
        // Check if email already exists in the database
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        UserRegistration newUser = new UserRegistration(userDTO);
        LocalDate currentDate=LocalDate.now();
        newUser.setRegisteredDate(currentDate);
        userRepository.save(newUser);

        String otp = sendOtp(newUser.getEmail());
        mailService.sendEmail(newUser.getEmail(), "User registration otp", "Don't share this otp to anyone: " + otp);

        return "User registered successfully. An OTP has been sent to your email for verification.";
    }

    @Override
    public String loginUser(String email_id, String password) {
        UserRegistration login = userRepository.findByEmailid(email_id);
        if (login != null) {
            String pass = login.getPassword();
            if (pass.equals(password)) {
                // Generate and return token
                String token = util.createToken(login.getUserId());
                return token;
            } else {
                throw new BookStoreException("Wrong Password");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public String sendOtp(String email) {
        UserRegistration userRegistration = userRepository.findByEmail(email);
        if (userRegistration != null) {
            String otp = generateOTP();
            userRegistration.setOtp(otp);
            userRepository.save(userRegistration);
            // Send OTP to user's email (you need to implement this part)
            return otp;
        }
        return null;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        UserRegistration userRegistration = userRepository.findByEmail(email);
        return userRegistration != null && userRegistration.getOtp().equals(otp);
    }

    @Override
    public String generateOTP() {
        // Generate a random 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    @Override
    public String generateAndSendOtpForForgotPassword(String email) {
        String otp = generateOTP(); // Implement your OTP generation logic here
        mailService.sendForgotPasswordOtpEmail(email, otp); // Send OTP via email
        return otp;
    }

    // Verify OTP and reset password
    @Override
    public void resetPassword(String email, String newPassword) {
        UserRegistration user = userRepository.findByEmail(email);
        if (user != null) {
            // Set the new password and save the user
            user.setPassword(newPassword);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found for email: " + email);
        }
    }

    @Override
    public List<UserRegistration> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Object getUserById(String token) {
        int id = util.decodeToken(token);
        Optional<UserRegistration> getUser = userRepository.findById(id);
        if (getUser == null) {
            throw new BookStoreException("Record for provided userId is not found");
        } else {
            mailService.sendEmail("skhanvat@gmail.com", "Test Email",
                    "Get your data with this token, hii: " + getUser.getClass()
                            + "Please Click here to get data-> " + "http://localhost:8080/user/getBy/" + token);
            return getUser;
        }
    }

    @Override
    public UserRegistration updateUser(String email_id, UserDTO userDTO) {
        UserRegistration user = userRepository.findByEmailid(email_id);
        if (user == null) {
            throw new BookStoreException("User Details for email not found");
        }
        UserRegistration newUser = new UserRegistration(user.getUserId(), userDTO);
        userRepository.save(newUser);
        String token = util.createToken(newUser.getUserId());
        mailService.sendEmail(newUser.getEmail(), "Welcome " + newUser.getFirstName(),
                "Click here \n http://localhost:8080/user/getBy/" + token);
        return newUser;
    }

    @Override
    public String getToken(String email) {
        UserRegistration userRegistration = userRepository.findByEmailid(email);
        String token = util.createToken(userRegistration.getUserId());
        mailService.sendEmail(userRegistration.getEmail(), "Welcome " + userRegistration.getFirstName(),
                "Token for changing password is: " + token);
        return token;
    }



    @Override
    public ResponseEntity<ResponseDTO> deleteUserById(Integer id) {
        UserRegistration userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(userToDelete);
        ResponseDTO responseDTO = new ResponseDTO("User deleted successfully", userToDelete);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Override
    public UserRegistration updateRecordById(Integer id, UserDTO userDTO) {
        Optional<UserRegistration> user = userRepository.findById(id);
        if (user != null) {
            UserRegistration newUser = new UserRegistration(id, userDTO);
            LocalDate currentDate=LocalDate.now();
            newUser.setUpdatedDate(currentDate);
            userRepository.save(newUser);
            return newUser;
        } else {
            throw new UserNotFoundException("User Details for id not found");
        }
    }


}
