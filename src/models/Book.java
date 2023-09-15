package models;

public class Book {
    public Integer id;
    public String title;
    public Integer author_id;
    public Integer isbn;
    public Integer quantite;

    public Book(Integer id, String title, Integer author_id, Integer isbn, Integer quantite) {
        this.id = id;
        this.title = title;
        this.author_id = author_id;
        this.isbn = isbn;
        this.quantite = quantite;
    }

    public Book() {

    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthor_id() {
        return this.author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
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

    public static String special() {
        return "isbn";
    }

}
