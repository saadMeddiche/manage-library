package models;

public class Book {

    private Integer id;
    private String title;
    private String author;
    private Integer isbn;
    private Integer quantite;

    public Book(Integer id, String title, String author, Integer isbn, Integer quantite) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantite = quantite;
    }

    public Book() {

    }

    // Getters & Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getIsbn() {
        return this.isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Integer getQuantite() {
        return this.quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

}
