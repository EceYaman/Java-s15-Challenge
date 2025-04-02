package com.library.service;

import com.library.model.Invoice;

public interface PaymentService {
    void processPayment(Invoice invoice);
    void refundPayment(Invoice invoice);
}
