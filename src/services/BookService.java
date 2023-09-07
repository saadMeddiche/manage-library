//# How to call static method in java
//# Constructors
//# SQL Between
package services;

import java.util.List;

import models.Book;

public class BookService extends Service {

    public List<Object> searchByAuthor(String author) {

        List<Object> objects = super.search(Book.class, "books", "author", author);

        return objects;
    }

    public List<Object> searchByTitle(String title) {

        List<Object> objects = super.search(Book.class, "books", "Title", title);

        return objects;
    }
}
