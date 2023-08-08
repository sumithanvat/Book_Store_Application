package com.bridgelabz.BookStore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Data;
    @Data
    public class UserLoginDTO {
        @Email
        private String email;
        @NotEmpty(message = "Password cant be null")
        private String password;
        public UserLoginDTO(String email,String password){
            this.email=email;
            this.password=password;
        }

    }
