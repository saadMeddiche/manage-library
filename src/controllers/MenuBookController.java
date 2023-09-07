// #ClearConsole

package controllers;

import views.ViewBook;

public class MenuBookController extends MenuController {

    public MenuBookController() {
        super(new ViewBook(), "isbn");
    }

    @Override
    protected String[] options() {
        return new String[] {
                "Add Book",
                "View All Books",
                "Update Book",
                "Delete Book",
                "Return"
        };
    }

}
