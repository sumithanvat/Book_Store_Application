package com.bridgelabz.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int userId;
    private int bookId;
    private int quantity;
    private String address;

    public Object isCancel() {
        return null;
    }
}
