package com.library.service;

import com.library.model.Book;
import com.library.model.BookStatus;
import com.library.model.Reader;

import java.util.HashMap;
import java.util.Map;

public class LibraryService {
    private Map<Integer, Book> books;

    public LibraryService() {
        books = new HashMap<>();
    }

    public void borrowBook(Book book, Reader reader) {
        if (reader.getBorrowedBooks().size() >= 5) {
            System.out.println("Maksimum 5 kitap alınabilir.");
            return;
        }


        if (book.getStatus() == BookStatus.AVAILABLE) {
            reader.addBorrowedBook(book);
            book.markAsBorrowed();
            System.out.println(book.getTitle() + " ödünç alındı.");
        } else {
            System.out.println("Kitap mevcut değil, ödünç alınamaz.");
        }
    }


    public void returnBook(Book book, Reader reader) {
        if (reader.getBorrowedBooks().contains(book)) {
            reader.removeBorrowedBook(book);
            book.markAsAvailable();
            System.out.println(book.getTitle() + " iade edildi.");
        } else {
            System.out.println("Bu kitap ödünç alınmamış.");
        }
    }


    public void listBooksByCategory(String categoryName) {
        System.out.println("Kategoriye göre kitaplar:");
        for (Book book : books.values()) {
            if (book.getCategory().getCategoryName().equalsIgnoreCase(categoryName)) {
                System.out.println("Kitap ID: " + book.getId() + " | " + book.getTitle() + " | Yazar: " + book.getAuthor().getName());
            }
        }
    }

    public void listBooksByAuthor(String authorName) {
        System.out.println("Yazara göre kitaplar:");
        for (Book book : books.values()) {
            if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
                System.out.println("Kitap ID: " + book.getId() + " | " + book.getTitle() + " | Kategori: " + book.getCategory().getCategoryName());
            }
        }
    }


    public void addBook(Book book) {
        books.put(book.getId(), book);
        System.out.println(book.getTitle() + " kütüphaneye eklendi.");
    }


    public void deleteBook(int bookId) {
        Book removedBook = books.remove(bookId);
        if (removedBook != null) {
            System.out.println(removedBook.getTitle() + " kütüphaneden silindi.");
        } else {
            System.out.println("Silinecek kitap bulunamadı.");
        }
    }


    public void listAllBooks() {
        System.out.println("Tüm kitaplar:");
        for (Book book : books.values()) {
            System.out.println("Kitap ID: " + book.getId() + " | " + book.getTitle() + " | Yazar: " + book.getAuthor().getName());
        }
    }
}
