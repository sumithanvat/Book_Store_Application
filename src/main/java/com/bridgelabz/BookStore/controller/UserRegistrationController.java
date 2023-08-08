package com.bridgelabz.BookStore.controller;
import com.bridgelabz.BookStore.dto.ResponseDTO;
import com.bridgelabz.BookStore.dto.UserDTO;
import com.bridgelabz.BookStore.dto.UserLoginDTO;
import com.bridgelabz.BookStore.exception.EmailAlreadyExistsException;
import com.bridgelabz.BookStore.model.UserRegistration;
import com.bridgelabz.BookStore.repository.UserRegistrationRepository;
import com.bridgelabz.BookStore.service.IUserService;
import com.bridgelabz.BookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserRegistrationController {

    @Autowired
    IUserService userRegistrationService;

    //Add User
    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) throws EmailAlreadyExistsException {
        userRegistrationService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully. An OTP has been sent to your email for verification.");
    }
    @PostMapping("/verifyotp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = userRegistrationService.verifyOtp(email, otp);
        if (isValid) {
            return "OTP verified successfully.";
        }
        return "Invalid OTP.";
    }

    //Login
    @GetMapping("/login")//postMapping
    public String userLogin(@RequestParam String email,@RequestParam String password) {
        UserLoginDTO userLoginDTO=new UserLoginDTO(email, password);
        String response = userRegistrationService.loginUser(userLoginDTO.getEmail(),userLoginDTO.getPassword());
        return response;
    }

    // Generate and Send OTP for Forgot Password
    @PostMapping("/forgotPassword")
    public ResponseEntity<ResponseDTO> forgotPassword(@RequestParam String email) {
        String otp = userRegistrationService.generateAndSendOtpForForgotPassword(email);
        ResponseDTO responseDTO = new ResponseDTO("OTP for resetting password sent successfully", otp);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    // Verify OTP for Forgot Password and Reset Password
    @PostMapping("/verifyForgotPasswordOtp")
    public ResponseEntity<ResponseDTO> verifyForgotPasswordOtp(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword
    ) {
        boolean isValid = userRegistrationService.verifyOtp(email, otp);

        if (isValid) {
            userRegistrationService.resetPassword(email, newPassword);
            ResponseDTO responseDTO = new ResponseDTO("Password reset successfully", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = new ResponseDTO("Invalid OTP", null);
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    //Get All Users
    @GetMapping(value = "/getAll")
    public ResponseEntity<String> getAllUser()
    {
        List<UserRegistration> listOfUsers = userRegistrationService.getAllUsers();
        ResponseDTO dto = new ResponseDTO("User retrieved successfully (:",listOfUsers);
        return new ResponseEntity(dto,HttpStatus.OK);
    }


    //Get user by user id
    @GetMapping("/getBy/{token}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable String token) {
        return new ResponseEntity<ResponseDTO>( new
                ResponseDTO("Get User Data By Id",
                userRegistrationService.getUserById(token)), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRecordById(@PathVariable Integer id,@Valid @RequestBody UserDTO userDTO){
        UserRegistration entity = userRegistrationService.updateRecordById(id,userDTO);
        ResponseDTO dto = new ResponseDTO("User Record updated successfully",entity);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
    // Delete User by ID
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<ResponseDTO> deleteUserById(@PathVariable Integer id) {
//        ResponseEntity<ResponseDTO> deletedUser = userRegistrationService.deleteUserById(id);
//
//        if (deletedUser != null) {
//            ResponseDTO responseDTO = new ResponseDTO("User deleted successfully", deletedUser);
//            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
//        } else {
//            ResponseDTO responseDTO = new ResponseDTO("User not found", null);
//            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/getToken/{email}")
    public ResponseEntity<ResponseDTO> getToken(@PathVariable String email){
        String generatedToken=userRegistrationService.getToken(email);
        ResponseDTO responseDTO=new ResponseDTO("Token for mail id sent on mail successfully",generatedToken);
        return new ResponseEntity(responseDTO,HttpStatus.OK);
    }
}