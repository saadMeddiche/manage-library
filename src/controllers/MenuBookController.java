// #ClearConsole

package controllers;

import views.ViewBook;

public class MenuBookController extends MenuController {

    public MenuBookController() {
        super(new ViewBook());
    }

    @Override
    protected String[] options() {
        return new String[] {
                "Add Book",
                "View All Books",
                "Update Book",
                "Delete Book",
                "Exit"
        };
    }

}
