package com.abdalrahman.springcommerce.order.dtos;

import com.abdalrahman.springcommerce.order.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {

    @NotNull(message = "{order.status.required}")
    private OrderStatus status;
}
