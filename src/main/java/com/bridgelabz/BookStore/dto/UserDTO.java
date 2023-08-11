package com.bridgelabz.BookStore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd/MMM/yyyy")
    private LocalDate dob;
    @NotEmpty
    private String password;
    @Email
    private String email;
}

