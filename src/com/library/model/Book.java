package com.library.model;

public class Book {
    private int id;
    private String title;
    private Author author;
    private Category category;
    private BookStatus status;

    public Book(int id, String title, Author author, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author.getName()
                + ", category=" + category.getCategoryName() + ", status=" + status.getDescription() + "]";
    }
}
