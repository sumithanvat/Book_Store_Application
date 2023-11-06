package com.bridgelabz.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer bookId;
    @NotNull
    private Integer quantity;
    @NotEmpty
    private String address;
//    private boolean cancel;

}

