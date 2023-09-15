package models;

import java.util.Objects;

public class Rapport {

    public Rapport() {
    }

    public Rapport(Integer id, Integer book_id, Integer status_id, Integer quantite) {
        this.id = id;
        this.book_id = book_id;
        this.status_id = status_id;
        this.quantite = quantite;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBook_id() {
        return this.book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getStatus_id() {
        return this.status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Integer getQuantite() {
        return this.quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public static String special() {
        return "id";
    }

    public Integer id;
    public Integer book_id;
    public Integer status_id;
    public Integer quantite;

}
