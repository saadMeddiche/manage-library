package controllers;

import helpers.helper;

public class MenuManageController extends MenuController {

    @Override
    protected String[] options() {
        return new String[] {
                "Manage Books",
                "Manage Clients",
                "Manage BorrowedBooks",
                "Manage Rapports",
                "Manage Statues",
                "Exist"
        };
    }

    @Override
    protected void excuteChoice(int choice) {
        switch (choice) {
            case 0:
                MenuBookController menuBookController = new MenuBookController();
                menuBookController.start();
                break;
            case 1:
                MenuClientController menuClientController = new MenuClientController();
                menuClientController.start();
                break;
            case 2:
                System.out.println("Coming Soon !");
                helper.stopProgramUntilButtonIsCliqued();
                break;
            case 3:
                System.out.println("Coming Soon !");
                helper.stopProgramUntilButtonIsCliqued();
                break;
            case 4:
                System.out.println("Coming Soon !");
                helper.stopProgramUntilButtonIsCliqued();
                break;
            case 5:
                System.out.println("Lay3awen!");
                System.exit(0);
                break;
            default:
                System.out.println("wa haaaad choice makaynx :/");
        }
    }
}
