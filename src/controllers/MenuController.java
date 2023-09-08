// #ClearConsole
// #Call Constructure Parent
// #Colors In Console
package controllers;

import java.io.File;
import java.util.Scanner;

import helpers.helper;
import views.View;

public abstract class MenuController {

    private View view;
    private String whereColumn;

    protected MenuController() {
    }

    protected MenuController(View view, String whereColumn) {
        this.view = view;
        this.whereColumn = whereColumn;
    }

    public void start() {

        try {
            Integer selectedOption = 0;

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

                    if (selectedOption.equals(4)) {
                        break;
                    }

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
                view.update(whereColumn);
                break;
            case 3:
                view.delete(whereColumn);
                break;
            case 4:
                System.out.println("Lay3awen!");
                System.exit(0);
                break;
            default:
                System.out.println("wa haaaad choice makaynx :/");
        }
    }

    public static Class<?>[] findModels() {

        try {

            File packageDirectory = new File("C:\\Users\\YouCode\\Desktop\\Library\\bin\\models");

            String[] files = packageDirectory.list();

            Class<?>[] classes = new Class<?>[files.length];

            int index = 0;

            for (String file : files) {

                StringBuilder sb = new StringBuilder(file);

                // remove .class
                sb.setLength(sb.length() - 6);

                Class<?> c = getClass(sb.toString());

                classes[index] = c;

                index++;
            }

            return classes;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Class<?> getClass(String className) {
        try {
            return Class.forName("models." + className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract String[] options();

}
