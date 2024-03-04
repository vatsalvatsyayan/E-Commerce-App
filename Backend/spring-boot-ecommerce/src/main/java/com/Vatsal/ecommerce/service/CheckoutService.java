package com.Vatsal.ecommerce.service;

import com.Vatsal.ecommerce.dto.Purchase;
import com.Vatsal.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    
    PurchaseResponse placeOrder(Purchase purchase);
}
