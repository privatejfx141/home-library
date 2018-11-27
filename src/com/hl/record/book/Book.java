package com.hl.record.book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hl.gui.HomeLibrary;
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

    public static class Builder {
        private Book book = new Book();

        public Builder setIsbn(String isbn) {
            try {
                book.isbn = isbn.trim();
            } catch (NullPointerException e) {
                book.isbn = "";
            }
            return this;
        }

        public Builder setTitle(String title) {
            try {
                book.title = title.trim();
            } catch (NullPointerException e) {
                book.title = "";
            }
            return this;
        }

        public Builder setPublisher(String publisher) {
            try {
                book.publisher = publisher.trim();
            } catch (NullPointerException e) {
                book.publisher = "";
            }
            return this;
        }

        public Builder setNumberOfPages(int pages) {
            book.pages = pages;
            return this;
        }

        public Builder setNumberOfPages(String pages) throws NumberFormatException {
            try {
                pages = pages.trim();
                return setNumberOfPages(Integer.parseInt(pages));
            } catch (NullPointerException | NumberFormatException e) {
                String msg = String.format(HomeLibrary.INTEGER_FIELD_MSG, "Number of pages");
                throw new NumberFormatException(msg);
            }
        }

        public Builder setYearOfPublication(int year) {
            book.year = year;
            return this;
        }

        public Builder setYearOfPublication(String year) throws NumberFormatException {
            try {
                year = year.trim();
                return setYearOfPublication(Integer.parseInt(year));
            } catch (NullPointerException | NumberFormatException e) {
                String msg = String.format(HomeLibrary.INTEGER_FIELD_MSG, "Year of publication");
                throw new NumberFormatException(msg);
            }
        }

        public Builder setEditionNumber(int edition) {
            book.edition = edition;
            return this;
        }

        public Builder setEditionNumber(String edition) {
            if (edition != null && !(edition = edition.trim()).isEmpty()) {
                try {
                    return setYearOfPublication(Integer.parseInt(edition));
                } catch (NumberFormatException e) {
                    String msg = String.format(HomeLibrary.INTEGER_FIELD_MSG, "Edition number");
                    throw new NumberFormatException(msg);
                }
            }
            return this;
        }

        public Builder setDescription(String description) {
            try {
                book.description = description.trim();
            } catch (NullPointerException e) {
                book.description = "";
            }
            return this;
        }

        public Builder addAuthor(Person author) {
            book.authors.add(author);
            return this;
        }

        public Builder addAuthors(Collection<Person> authors) {
            book.authors.addAll(authors);
            return this;
        }

        public Builder addKeyword(String keyword) {
            book.keywords.add(keyword.trim());
            return this;
        }

        public Builder addKeywords(Collection<String> keywords) {
            book.keywords.addAll(keywords);
            return this;
        }

        public Book create() {
            return book;
        }
    }

    private Book() {
        authors = new ArrayList<>();
        keywords = new ArrayList<>();
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
