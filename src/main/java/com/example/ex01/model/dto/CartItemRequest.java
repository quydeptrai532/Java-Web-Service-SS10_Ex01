package com.example.ex01.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    @NotBlank(message = "UserId ko được để trống")
    private String userId;
    @NotBlank(message = "ProductId ko được để trống")
    private String productId;
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1,message = "Số lượng phải lớn hơn 0")
    private Integer quantity;

}
