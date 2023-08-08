package com.bridgelabz.BookStore.model;

import com.bridgelabz.BookStore.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "UserRegistration")
public class UserRegistration {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer userId;
        private String firstName;
        private String lastName;
        private LocalDate dob;
        private LocalDate registeredDate;
        private LocalDate updatedDate;
        private String password;
        private String email;
        private boolean verify;
        private String otp;
        public UserRegistration(UserDTO userDTO){
                this.userId=getUserId();
                this.firstName= userDTO.getFirstName();
                this.lastName= userDTO.getLastName();
                this.dob=userDTO.getDob();
                this.password= userDTO.getPassword();
                this.email= userDTO.getEmail();
        }
        public UserRegistration(Integer userId,UserDTO userDTO){
                this.userId=getUserId();
                this.firstName= userDTO.getFirstName();
                this.lastName= userDTO.getLastName();
                this.dob=userDTO.getDob();
                this.password= userDTO.getPassword();
                this.email= userDTO.getEmail();

        }

}


