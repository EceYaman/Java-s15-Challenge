package com.library.model;

public class Invoice {
    private int invoiceId;
    private Reader reader;
    private Book book;
    private double amount;

    public Invoice(int invoiceId, Reader reader, Book book, double amount) {
        this.invoiceId = invoiceId;
        this.reader = reader;
        this.book = book;
        this.amount = amount;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Invoice [id=" + invoiceId + ", reader=" + reader.getName() + ", book=" + book.getTitle() + ", amount=" + amount + "]";
    }
}
