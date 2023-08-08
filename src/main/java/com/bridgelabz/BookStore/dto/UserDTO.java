package com.bridgelabz.BookStore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@Data
public class UserDTO {
    @Pattern(regexp="^[A-Z]{1}[a-zA-Z\\s]{2,}$",message="Employee firstName is Not valid")
    private String firstName;
    @Pattern(regexp="^[A-Z]{1}[a-zA-Z\\s]{2,}$",message="Employee lastName is Not valid")
    private String lastName;
    private LocalDate dob;
    //private LocalDate registeredDate;
    //private LocalDate updatedDate;
    @NotEmpty
    private String password; // Use password encoder
    @Email
    private String email;
}

