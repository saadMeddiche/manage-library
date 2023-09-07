package controllers;

import views.ViewClient;

public class MenuClientController extends MenuController {

    public MenuClientController() {
        super(new ViewClient(), "cin");
    }

    @Override
    protected String[] options() {
        return new String[] {
                "Add Client",
                "View All Clients",
                "Update Client",
                "Delete Client",
                "Return"
        };
    }
}
