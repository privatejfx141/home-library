package com.hl.record.book;

import java.util.ArrayList;
import java.util.List;

import com.hl.record.Person;

public class Book {

    public static class Builder {
        private Book book = new Book();

        public Builder setIsbn(String isbn) {
            book.isbn = isbn;
            return this;
        }

        public Builder setTitle(String title) {
            book.title = title;
            return this;
        }

        public Builder setPublisher(String publisher) {
            book.publisher = publisher;
            return this;
        }

        public Builder setNumberOfPages(int pages) {
            book.pages = pages;
            return this;
        }

        public Builder setYearOfPublication(int year) {
            book.year = year;
            return this;
        }

        public Builder setEditionNumber(int edition) {
            book.edition = edition;
            return this;
        }

        public Builder setDescription(String description) {
            book.description = description;
            return this;
        }

        public Builder addAuthor(Person author) {
            book.authors.add(author);
            return this;
        }

        public Builder addKeyword(String keyword) {
            book.keywords.add(keyword);
            return this;
        }

        public Book create() {
            return book;
        }
    }

    private String isbn = "";
    private String title = "";
    private String publisher = "";
    private int pages = -1;
    private int year = -1;
    private int edition = -1;
    private String description = "";
    private List<Person> authors;
    private List<String> keywords;

    private Book() {
        authors = new ArrayList<>();
        keywords = new ArrayList<>();
    }

    public Book(String isbn, String title, String publisher, int pages, int year, int edition, String description,
            List<Person> authors, List<String> keywords) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.pages = pages;
        this.year = year;
        this.edition = edition;
        this.description = description;
        this.authors = authors;
        this.keywords = keywords;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getNumberOfPages() {
        return pages;
    }

    public int getYearOfPublication() {
        return year;
    }

    public int getEditionNumber() {
        return edition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public void addAuthor(Person author) {
        authors.add(author);
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void addKeyword(String keyword) {
        keywords.add(keyword);
    }
}
