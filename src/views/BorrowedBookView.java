package views;

import models.BorrowedBook;

public class BorrowedBookView extends View {

    public BorrowedBookView() {
        super(BorrowedBook.class);
    }

    @Override
    public String[] options() {

        return new String[] {
                "Borrow Book",
                "Search Borrowed Books",
                "Show Borrowed Books",
                "Update Borrowed Books",
                "Return Borrowed Books",
                "Return"
        };
    }

}
