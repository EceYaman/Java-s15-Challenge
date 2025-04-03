package com.library.model;

import java.util.HashSet;
import java.util.Set;

public class Book {
    private int id;
    private String title;
    private Author author;
    private Set<Category> categories;
    private BookStatus status;

    public Book(int id, String title, Author author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.categories = new HashSet<>();
        this.status = BookStatus.AVAILABLE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public void markAsBorrowed() {
        this.status = BookStatus.BORROWED;
    }

    public void markAsAvailable() {
        this.status = BookStatus.AVAILABLE;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    @Override
    public String toString() {
        StringBuilder catList = new StringBuilder();
        for (Category cat : categories) {
            catList.append(cat.getCategoryName()).append(" ");
        }
        return "Book [id=" + id + ", title=" + title + ", author=" + author.getName() +
                ", categories=" + catList.toString().trim() + ", status=" + status.getDescription() + "]";
    }
}
