// #ClearConsole
// #Call Constructure Parent
// #Colors In Console
package controllers;

import java.io.File;
import java.util.Scanner;

import helpers.helper;
import views.View;

public abstract class Menu {

    protected Menu() {
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

                    if (selectedOption.equals(options().length)) {
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

    public void displayMenu(int selectedOption) throws Exception {
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
            String x = className.substring(0, 1).toUpperCase() + className.substring(1);
            return Class.forName("models." + x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract String[] options();

    public abstract void excuteChoice(int choice);

}
