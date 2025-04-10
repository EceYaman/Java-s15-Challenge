package com.library.main;

import com.library.model.*;
import com.library.exception.LibraryException;
import com.library.service.LibraryService;
import com.library.service.PaymentService;
import com.library.service.PaymentServiceImpl;
import com.library.util.IdGenerator;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentServiceImpl();
        LibraryService libraryService = new LibraryService(paymentService);
        Scanner scanner = new Scanner(System.in);

        Author orhanPamuk = new Author(IdGenerator.generateId("Author"), "Orhan Pamuk", "Ünlü roman 'Benim Adım Kırmızı'nın yazarı");
        Book pamukBook1 = new Book(IdGenerator.generateId("Book"), "Benim Adım Kırmızı", orhanPamuk);
        pamukBook1.addCategory(new Category(CategoryType.HISTORY));
        libraryService.addBook(pamukBook1);

        Book pamukBook2 = new Book(IdGenerator.generateId("Book"), "Kar", orhanPamuk);
        pamukBook2.addCategory(new Category(CategoryType.FANTASY));
        libraryService.addBook(pamukBook2);

        Author yasarKemal = new Author(IdGenerator.generateId("Author"), "Yaşar Kemal", "Ünlü roman 'İnce Memed'in yazarı");
        Book kemalBook1 = new Book(IdGenerator.generateId("Book"), "İnce Memed", yasarKemal);
        kemalBook1.addCategory(new Category(CategoryType.FICTION));
        libraryService.addBook(kemalBook1);

        Book kemalBook2 = new Book(IdGenerator.generateId("Book"), "Yer Demir Gök Bakır", yasarKemal);
        kemalBook2.addCategory(new Category(CategoryType.SCIENCE));
        libraryService.addBook(kemalBook2);

        Author sabahattinAli = new Author(IdGenerator.generateId("Author"), "Sabahattin Ali", "Ünlü roman 'Kürk Mantolu Madonna'nın yazarı");
        Book aliBook1 = new Book(IdGenerator.generateId("Book"), "Kürk Mantolu Madonna", sabahattinAli);
        aliBook1.addCategory(new Category(CategoryType.ROMANCE));
        libraryService.addBook(aliBook1);

        Book aliBook2 = new Book(IdGenerator.generateId("Book"), "İçimizdeki Şeytan", sabahattinAli);
        aliBook2.addCategory(new Category(CategoryType.FICTION));
        libraryService.addBook(aliBook2);

        Reader reader1 = new Reader(IdGenerator.generateId("Reader"), "Ali Yılmaz", "ali@gmail.com");
        libraryService.addReader(reader1);

        Reader reader2 = new Reader(IdGenerator.generateId("Reader"), "Veli Yıldırım", "veli@gmail.com");
        libraryService.addReader(reader2);

        while (true) {
            System.out.println("\n-- KÜTÜPHANE MENÜSÜ --");
            System.out.println("1. Kitap Ekle");
            System.out.println("2. Kitap Ara (ID)");
            System.out.println("3. Kitap Ara (Başlık)");
            System.out.println("4. Kitap Ara (Yazar)");
            System.out.println("5. Kitap Sil");
            System.out.println("6. Kitap Ödünç Al");
            System.out.println("7. Kitap İade Et");
            System.out.println("8. Kategoriye Göre Kitap Listele");
            System.out.println("9. Yazara Göre Kitap Listele");
            System.out.println("10. Tüm Kitapları Listele");
            System.out.println("11. Kitap Bilgilerini Güncelle");
            System.out.println("12. Çıkış");
            System.out.print("******Seçiminiz(Menüden numara giriniz): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        // Yeni kitap ekleme
                        System.out.print("******Kitap Başlığı: ");
                        String title = scanner.nextLine();
                        System.out.print("******Yazar Adı: ");
                        String newAuthorName = scanner.nextLine();
                        System.out.print("******Yazar Biyografisi: ");
                        String bio = scanner.nextLine();
                        Author newAuthor = new Author(IdGenerator.generateId("Author"), newAuthorName, bio);

                        Book newBook = new Book(IdGenerator.generateId("Book"), title, newAuthor);
                        System.out.print("******Kaç kategori gireceksiniz? ");
                        int catCount = scanner.nextInt();
                        scanner.nextLine(); // dummy read
                        for (int i = 0; i < catCount; i++) {
                            System.out.print("******Kategori " + (i + 1) + " (FICTION, ROMANCE, SCIENCE, HISTORY, FANTASY): ");
                            String catStr = scanner.nextLine();
                            Category newCategory = new Category(CategoryType.valueOf(catStr.toUpperCase()));
                            newBook.addCategory(newCategory);
                        }
                        libraryService.addBook(newBook);
                        break;
                    case 2:
                        // ID ile kitap bulma
                        System.out.print("******Kitap ID: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine();
                        Book foundBook = libraryService.findBookById(bookId);
                        if (foundBook != null) {
                            System.out.println(foundBook);
                        } else {
                            System.out.println("******Kitap bulunamadı.");
                        }
                        break;
                    case 3:
                        // Başlık ile kitap arama
                        System.out.print("******Kitap Başlığı: ");
                        String searchTitle = scanner.nextLine();
                        List<Book> booksByTitle = libraryService.findBookByTitle(searchTitle);
                        if (booksByTitle.isEmpty()) {
                            System.out.println("******Aranan başlıkta kitap bulunamadı.");
                        } else {
                            booksByTitle.forEach(System.out::println);
                        }
                        break;
                    case 4:
                        // Yazar ismiyle kitap arama
                        System.out.print("******Yazar Adı: ");
                        String searchAuthor = scanner.nextLine();
                        List<Book> booksByAuthor = libraryService.findBookByAuthor(searchAuthor);
                        if (booksByAuthor.isEmpty()) {
                            System.out.println("******Aranan yazara ait kitap bulunamadı.");
                        } else {
                            booksByAuthor.forEach(System.out::println);
                        }
                        break;
                    case 5:
                        // Kitap silme
                        System.out.print("******Silinecek Kitap ID: ");
                        int delId = scanner.nextInt();
                        scanner.nextLine();
                        libraryService.deleteBook(delId);
                        break;
                    case 6:
                        // Kitap ödünç alma
                        System.out.print("******Okuyucu ID: ");
                        int idReader = scanner.nextInt();
                        System.out.print("******Kitap ID: ");
                        int idBook = scanner.nextInt();
                        scanner.nextLine();
                        libraryService.borrowBook(idBook, idReader);
                        break;
                    case 7:
                        // Kitap iade etme
                        System.out.print("******Okuyucu ID: ");
                        int rId = scanner.nextInt();
                        System.out.print("******Kitap ID: ");
                        int bId = scanner.nextInt();
                        scanner.nextLine();
                        libraryService.returnBook(bId, rId);
                        break;
                    case 8:
                        // Kategoriye göre kitap listeleme
                        System.out.print("******Kategori Adı: ");
                        String catName = scanner.nextLine();
                        List<Book> booksByCategory = libraryService.listBooksByCategory(catName);
                        if (booksByCategory.isEmpty()) {
                            System.out.println("*Aranan kategoriye ait kitap bulunamadı.");
                        } else {
                            booksByCategory.forEach(System.out::println);
                        }
                        break;
                    case 9:
                        // Yazara göre kitap listeleme
                        System.out.print("******Lütfen yazarın adını giriniz: ");
                        String authorName = scanner.nextLine();

                        List<Book> authorBooks = libraryService.listBooksByAuthor(authorName);
                        if (authorBooks.isEmpty()) {
                            System.out.println("******Bu yazara ait kitap bulunamadı.");
                        } else {
                            System.out.println("------" + authorName + " tarafından yazılan kitaplar:");
                            for (Book book : authorBooks) {
                                System.out.println(book);
                            }
                        }
                        break;
                    case 10:
                        // Tüm kitapları listeleme
                        libraryService.listAllBooks();
                        break;
                    case 11:
                        // Kitap bilgisini güncelleme
                        System.out.print("******Güncellenecek Kitap ID: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        Book bookToUpdate = libraryService.findBookById(updateId);
                        if (bookToUpdate == null) {
                            System.out.println("******Güncellenecek kitap bulunamadı.");
                        } else {
                            System.out.print("******Yeni Kitap Başlığı: ");
                            String newTitle = scanner.nextLine();
                            System.out.print("******Yeni Yazar Adı: ");
                            String updAuthorName = scanner.nextLine();
                            System.out.print("******Yeni Yazar Biyografisi: ");
                            String updBio = scanner.nextLine();
                            Author updAuthor = new Author(bookToUpdate.getAuthor().getId(), updAuthorName, updBio);
                            Book updatedBook = new Book(bookToUpdate.getId(), newTitle, updAuthor);

                            System.out.print("******Kaç kategori gireceksiniz? ");
                            int updCatCount = scanner.nextInt();
                            scanner.nextLine();
                            for (int i = 0; i < updCatCount; i++) {
                                System.out.print("******Yeni Kategori " + (i + 1) + " (FICTION, ROMANCE, SCIENCE, HISTORY, FANTASY): ");
                                String updCatStr = scanner.nextLine();
                                Category updCategory = new Category(CategoryType.valueOf(updCatStr.toUpperCase()));
                                updatedBook.addCategory(updCategory);
                            }

                            libraryService.updateBook(updatedBook);
                        }
                        break;
                    case 12:
                        // Sistemden çıkış
                        System.out.println("******Sistemden çıkılıyor...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("******Geçersiz seçim.");
                }
            } catch (LibraryException e) {
                System.out.println("******Hata: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("******Geçersiz değer girildi: " + e.getMessage());
            }
        }
    }
}