package com.cafe.pricing;

import com.cafe.model.FoodOrder;
import com.cafe.model.Order;

public interface PricingService {
    double calculatePrice(Order order);
    double calculatePrice(FoodOrder order);
}
