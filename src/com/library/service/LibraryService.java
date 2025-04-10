package com.library.service;

import com.library.exception.LibraryException;
import com.library.model.Book;
import com.library.model.BookStatus;
import com.library.model.Invoice;
import com.library.model.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryService {
    private Map<Integer, Book> books;
    private Map<Integer, Reader> readers;
    private List<Invoice> invoices;
    private PaymentService paymentService;
    private int invoiceCounter = 1;
    private static final int FIXED_BORROW_FEE = 40;

    public LibraryService(PaymentService paymentService) {
        this.books = new HashMap<>();
        this.readers = new HashMap<>();
        this.invoices = new ArrayList<>();
        this.paymentService = paymentService;
    }

    // Yeni kitap ekleme
    public void addBook(Book book) {
        if (books.containsKey(book.getId())) {
            System.out.println("------Bu ID'ye sahip kitap zaten mevcut.");
            return;
        }
        books.put(book.getId(), book);
        System.out.println("------" + book.getTitle() + " kitabı kütüphaneye eklendi.");
    }

    // Kitap bilgilerini güncelleme
    public void updateBook(Book updatedBook) throws LibraryException {
        int bookId = updatedBook.getId();
        if (!books.containsKey(bookId)) {
            throw new LibraryException("------Güncellenecek kitap bulunamadı.");
        }
        books.put(bookId, updatedBook);
        System.out.println("------Kitap bilgileri güncellendi:");
        System.out.println("      -Kitap ID: " + updatedBook.getId());
        System.out.println("      -Kitap Adı: " + updatedBook.getTitle());
        System.out.println("      -Yazar: " + updatedBook.getAuthor().getName());
        System.out.println("      -Yazarın Biyografisi: " + updatedBook.getAuthor().getBiography());
        System.out.print("      -Kategori: ");
        updatedBook.getCategories().forEach(category -> System.out.print(category.getCategoryName() + " "));
        System.out.println();
        System.out.println("      -Kitap Durumu: " + updatedBook.getStatus().getDescription());
    }

    // Kitap silme
    public void deleteBook(int bookId) {
        Book removed = books.remove(bookId);
        if (removed != null) {
            System.out.println("------" + removed.getTitle() + " kitabı kütüphaneden silindi.");
        } else {
            System.out.println("------Silinecek kitap bulunamadı.");
        }
    }

    // ID'ye göre kitap arama
    public Book findBookById(int id) {
        return books.get(id);
    }

    // Başlığa göre kitap arama
    public List<Book> findBookByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                result.add(b);
            }
        }
        return result;
    }

    // Yazar adına göre kitap arama
    public List<Book> findBookByAuthor(String authorName) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.getAuthor().getName().equalsIgnoreCase(authorName)) {
                result.add(b);
            }
        }
        return result;
    }

    // Kategoriye göre kitap listeleme
    public List<Book> listBooksByCategory(String categoryName) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            boolean match = b.getCategories().stream()
                    .anyMatch(cat -> cat.getCategoryName().equalsIgnoreCase(categoryName));
            if (match) {
                result.add(b);
            }
        }
        return result;
    }

    // Yazara göre kitap listeleme
    public List<Book> listBooksByAuthor(String authorName) {
        return findBookByAuthor(authorName);
    }

    // Tüm kitapları listeleme
    public void listAllBooks() {
        System.out.println("------Tüm kitaplar:");
        for (Book b : books.values()) {
            System.out.println(b);
        }
    }


    // Okuyucu ekleme
    public void addReader(Reader reader) {
        if (readers.containsKey(reader.getId())) {
            System.out.println("------Bu ID'ye sahip okuyucu zaten mevcut.");
            return;
        }
        readers.put(reader.getId(), reader);
        System.out.println("------" + reader.getName() + " okuyucu olarak eklendi.");
    }

    public Reader findReaderById(int id) {
        return readers.get(id);
    }


    // Kitabı ödünç alma
    public void borrowBook(int bookId, int readerId) throws LibraryException {
        Reader reader = readers.get(readerId);
        Book book = books.get(bookId);

        if (reader == null) {
            throw new LibraryException("------Okuyucu bulunamadı.");
        }
        if (book == null) {
            throw new LibraryException("------Kitap bulunamadı.");
        }
        if (reader.getBorrowedBooks().size() >= reader.getBorrowLimit()) {
            throw new LibraryException("------Okuyucunun maksimum kitap ödünç alma limiti doldu.");
        }
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new LibraryException("------Bu kitap ödünç alınamaz.");
        }

        reader.addBorrowedBook(book);
        book.markAsBorrowed();
        System.out.println("------" + book.getTitle() + " kitabı " + reader.getName() + " tarafından ödünç alındı.");

        Invoice invoice = new Invoice(invoiceCounter++, reader, book, FIXED_BORROW_FEE);
        invoices.add(invoice);
        paymentService.processPayment(invoice);
    }

    // Kitabı iade etme
    public void returnBook(int bookId, int readerId) throws LibraryException {
        Reader reader = readers.get(readerId);
        Book book = books.get(bookId);

        if (reader == null) {
            throw new LibraryException("------Okuyucu bulunamadı.");
        }
        if (book == null) {
            throw new LibraryException("------Kitap bulunamadı.");
        }
        if (!reader.getBorrowedBooks().contains(book)) {
            throw new LibraryException("------Bu kitap, ilgili okuyucu tarafından ödünç alınmamış.");
        }

        reader.removeBorrowedBook(book);
        book.markAsAvailable();
        System.out.println("------" + book.getTitle() + " kitabı " + reader.getName() + " tarafından iade edildi.");


        Invoice invoice = findInvoice(reader, book);
        if (invoice != null) {
            paymentService.refundPayment(invoice);
        } else {
            System.out.println("------Ödeme iadesi için fatura bulunamadı.");
        }
    }


    // Belirli okuyucu ve kitaba ait fatura arama
    private Invoice findInvoice(Reader reader, Book book) {
        for (Invoice inv : invoices) {
            if (inv.getReader().equals(reader) && inv.getBook().equals(book)) {
                return inv;
            }
        }
        return null;
    }
}
