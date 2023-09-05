//# How to call static method in java
//# Constructors
//# SQL Between
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import database.Db;
import helpers.helper;
import models.Book;

public class BookService {

    private Connection connection = Db.makeConnection();

    public List<Book> getAllBooks() {
        List<Book> BookList = new ArrayList<Book>();

        try {

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");

            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                        Integer.valueOf(rs.getString("isbn")), rs.getInt("quantite"));

                BookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BookList;
    }

    public int add(Book book) {

        int status = 0;

        try {

            String query = "INSERT INTO books (title , author , isbn , quantite) VALUES(?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);

            // set parameters of query
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getIsbn());
            ps.setInt(4, book.getQuantite());

            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public Book findBookWithIsbn(Integer isbn) {
        Book book = null;
        try {

            String query = "SELECT * FROM books WHERE isbn=?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, isbn);
            ResultSet resultSet = ps.executeQuery();

            // Not found
            if (!resultSet.next()) {
                return null;
            }

            book = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("author"),
                    resultSet.getInt("isbn"), resultSet.getInt("quantite"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;

    }

    public int updateByIsbn(Book book, int isbn_searched_with) {

        int status = 0;

        try {

            String query = "UPDATE `books` SET `title`=?,`author`=?,`isbn`=?,`quantite`=? WHERE `isbn`=?";
            PreparedStatement ps = connection.prepareStatement(query);

            // set parameters of query
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getIsbn());
            ps.setInt(4, book.getQuantite());
            ps.setInt(5, isbn_searched_with);

            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public int destroyByIsbn(int book_isbn) {

        int status = 0;

        try {

            String query = "DELETE FROM `books` WHERE `isbn` = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            // set parameters of query
            ps.setInt(1, book_isbn);

            status = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public Boolean checkIfBookExist(int isbn) {
        return helper.checkIfExist("books", "isbn", Integer.toString(isbn));
    }

    public List<Book> getBookByPage(int row) {

        List<Book> BookList = new ArrayList<Book>();

        try {
            String query = "SELECT * FROM books LIMIT 5 OFFSET ?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, row);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                        Integer.valueOf(rs.getString("isbn")), rs.getInt("quantite"));

                BookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BookList;

    }

}
