package com.hl.record.book;

import java.util.List;

import com.hl.record.Person;

public class Book {
    private String isbn = "";
    private String title = "";
    private String publisher = "";
    private int pages = -1;
    private int year = -1;
    private int edition = -1;
    private String description = "";
    private List<Person> authors;
    private List<String> keywords;

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

    public List<Person> getAuthors() {
        return authors;
    }

    public List<String> getKeywords() {
        return keywords;
    }
}
