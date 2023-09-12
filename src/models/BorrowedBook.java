package models;


public class BorrowedBook {
    public Integer id;
    public Integer book_id;
    public Integer client_id;
    public String date_borrow_start;
    public String date_borrow_end;
    public Integer price;

    public BorrowedBook() {
    }

    public BorrowedBook(Integer id, Integer book_id, Integer client_id, String date_borrow_start,
            String date_borrow_end, Integer price) {
        this.id = id;
        this.book_id = book_id;
        this.client_id = client_id;
        this.date_borrow_start = date_borrow_start;
        this.date_borrow_end = date_borrow_end;
        this.price = price;
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

    public Integer getClient_id() {
        return this.client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

    public String getDate_borrow_start() {
        return this.date_borrow_start;
    }

    public void setDate_borrow_start(String date_borrow_start) {
        this.date_borrow_start = date_borrow_start;
    }

    public String getDate_borrow_end() {
        return this.date_borrow_end;
    }

    public void setDate_borrow_end(String date_borrow_end) {
        this.date_borrow_end = date_borrow_end;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public static String special() {
        return "id";
    }

}
