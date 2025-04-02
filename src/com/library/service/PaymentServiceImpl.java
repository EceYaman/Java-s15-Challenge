package com.library.service;

import com.library.model.Invoice;

public class PaymentServiceImpl implements PaymentService{
    @Override
    public void processPayment(Invoice invoice) {
        System.out.println("Fatura " + invoice.getInvoiceId() + " işlendi. Tutar: " + invoice.getAmount());
    }

    @Override
    public void refundPayment(Invoice invoice) {
        System.out.println("Fatura " + invoice.getInvoiceId() + " için " + invoice.getAmount() + " iade edildi.");
    }

}
