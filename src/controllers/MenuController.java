// #ClearConsole
// #Call Constructure Parent
// #Colors In Console
package controllers;

import java.util.Scanner;

import helpers.helper;
import views.View;

public abstract class MenuController {

    private View view;

    protected MenuController() {
    }

    protected MenuController(View view) {
        this.view = view;
    }

    public void start() {

        try {
            int selectedOption = 0;

            while (true) {
                helper.clearConsole();

                displayMenu(selectedOption);

                Scanner input = new Scanner(System.in);
                String key = input.next();

                if (key.equals("w") && selectedOption < options().length - 1) {
                    selectedOption = selectedOption + 1;

                } else if (key.equals("s") && selectedOption >= 1) {
                    selectedOption = selectedOption - 1;

                } else if (key.equals("c")) {
                    helper.clearConsole();

                    excuteChoice(selectedOption);
                }

                helper.clearConsole();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void displayMenu(int selectedOption) throws Exception {
        System.out.println("\u001B[32m======Menu======\u001B[0m");
        String[] options = options();
        for (int i = 0; i < options.length; i++) {
            if (i == selectedOption) {
                System.out.println("\u001B[32m" + "-> " + "\u001B[0m" + options[i]);
            } else {
                System.out.println("   " + options[i]);
            }
        }
    }

    protected void excuteChoice(int choice) {
        switch (choice) {
            case 0:
                view.add();
                break;
            case 1:
                view.showAll(0);
                break;
            case 2:
                view.update();
                break;
            case 3:
                view.delete();
                break;
            case 4:
                System.out.println("Lay3awen!");
                System.exit(0);
                break;
            default:
                System.out.println("wa haaaad choice makaynx :/");
        }
    }

    protected abstract String[] options();

}
