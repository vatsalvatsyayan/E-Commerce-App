package com.Vatsal.ecommerce.dto;

import java.util.Set;

import com.Vatsal.ecommerce.entity.Address;
import com.Vatsal.ecommerce.entity.Customer;
import com.Vatsal.ecommerce.entity.Order;
import com.Vatsal.ecommerce.entity.OrderItem;

import lombok.Data;

@Data
public class Purchase {
    
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
