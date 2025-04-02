package com.library.model;

public class Librarian extends Person{
    private String staffNumber;

    public Librarian(int id, String name, String email, String staffNumber) {
        super(id, name, email);
        this.staffNumber = staffNumber;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }


    public void checkInBook(Book book, Reader reader) {
        System.out.println("Kitap " + book.getTitle() + " " + reader.getName() + " tarafından check-in yapıldı.");
    }

    public void checkOutBook(Book book, Reader reader) {
        System.out.println("Kitap " + book.getTitle() + " " + reader.getName() + " tarafından check-out yapıldı.");
    }

    @Override
    public String toString() {
        return "Librarian [id=" + getId() + ", name=" + getName() + ", staffNumber=" + staffNumber + "]";
    }
}
